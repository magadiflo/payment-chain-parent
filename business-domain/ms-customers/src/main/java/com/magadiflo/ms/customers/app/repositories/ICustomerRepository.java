package com.magadiflo.ms.customers.app.repositories;

import com.magadiflo.ms.customers.app.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCode(String code);

    @Query("SELECT c FROM Customer AS c WHERE c.iban = ?1")
    Optional<Customer> findByAccount(String iban);

}
