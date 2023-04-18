package com.magadiflo.ms.customers.app.services.impl;

import com.magadiflo.ms.customers.app.entities.Customer;
import com.magadiflo.ms.customers.app.repositories.ICustomerRepository;
import com.magadiflo.ms.customers.app.services.ICustomerService;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
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
}
