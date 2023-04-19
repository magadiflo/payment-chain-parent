package com.magadiflo.ms.transactions.app.repositories;

import com.magadiflo.ms.transactions.app.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<List<Transaction>> findByAccountIban(String accountIban);
}
