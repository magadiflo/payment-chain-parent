package com.magadiflo.ms.customers.app.services;

import com.magadiflo.ms.customers.app.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    List<Customer> listAll();

    Optional<Customer> findById(Long id);

    Customer save(Customer customer);

    Optional<Customer> edit(Long id, Customer customer);

    Optional<Boolean> delete(Long id);
}
