package com.magadiflo.ms.customers.app.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.magadiflo.ms.customers.app.entities.Customer;
import com.magadiflo.ms.customers.app.repositories.ICustomerRepository;
import com.magadiflo.ms.customers.app.services.ICustomerService;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final WebClient.Builder webClientBuilder;
    private final HttpClient client = HttpClient.create()
            // Connection Timeout: is a period within which a connection between a client and server must be established
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            // Response Timeout: The maximun time we wait to receive a response after sending a request
            .responseTimeout(Duration.ofSeconds(1))
            // Read an write timeout: a read timeout occurs when no data was read within a certain period of time,
            // while the write timeout when a write operation cannot finish at a specific time
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    @Override
    @Transactional(readOnly = true)
    public List<Customer> listAll() {
        return this.customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return this.customerRepository.findById(id);
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        // Relacionando el customer con cada customerProduct
        customer.getCustomerProducts().forEach(customerProduct -> customerProduct.setCustomer(customer));
        return this.customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> edit(Long id, Customer customer) {
        return this.customerRepository.findById(id)
                .map(customerBD -> {
                    customerBD.setName(customer.getName());
                    customerBD.setPhone(customer.getPhone());
                    return Optional.of(this.customerRepository.save(customerBD));
                })
                .orElseGet(Optional::empty);
    }

    @Override
    @Transactional
    public Optional<Boolean> delete(Long id) {
        return this.customerRepository.findById(id)
                .map(customerBD -> {
                    this.customerRepository.deleteById(customerBD.getId());
                    return Optional.of(true);
                })
                .orElseGet(Optional::empty);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findByCode(String code) {
        return this.customerRepository.findByCode(code)
                .map(customerBD -> {
                    customerBD.getCustomerProducts().forEach(customerProduct -> {
                        String productName = this.getProductName(customerProduct.getProductId());
                        customerProduct.setProductName(productName);
                    });
                    customerBD.setTransactions(this.getTransactions(customerBD.getIban()));

                    return Optional.of(customerBD);
                })
                .orElseGet(Optional::empty);
    }

    private String getProductName(Long productId) {
        WebClient webClient = this.webClientBuilder.clientConnector(new ReactorClientHttpConnector(this.client))
                .baseUrl("http://localhost:8083/api/v0/products")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8083/api/v0/products"))
                .build();

        JsonNode block = webClient.method(HttpMethod.GET)
                .uri("/" + productId)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        String name = block.get("name").asText();
        return name;
    }

    private List<?> getTransactions(String iban) {
        WebClient webClient = this.webClientBuilder.clientConnector(new ReactorClientHttpConnector(this.client))
                .baseUrl("http://localhost:8082/api/v0/transactions")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path("/transactions-of-client").queryParam("accountIban", iban).build())
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .block();
    }
}
