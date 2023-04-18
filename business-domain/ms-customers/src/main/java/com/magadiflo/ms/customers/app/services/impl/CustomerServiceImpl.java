package com.magadiflo.ms.customers.app.services.impl;

import com.magadiflo.ms.customers.app.entities.Customer;
import com.magadiflo.ms.customers.app.repositories.ICustomerRepository;
import com.magadiflo.ms.customers.app.services.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;

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
