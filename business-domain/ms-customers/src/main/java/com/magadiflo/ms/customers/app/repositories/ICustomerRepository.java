package com.magadiflo.ms.customers.app.repositories;

import com.magadiflo.ms.customers.app.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
}
