package com.magadiflo.ms.transactions.app.services;

import com.magadiflo.ms.transactions.app.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface ITransactionService {
    List<Transaction> listAll();

    Optional<Transaction> findById(Long id);

    Transaction save(Transaction transaction);

    Optional<Transaction> edit(Long id, Transaction transaction);

    Optional<Boolean> delete(Long id);

    Optional<List<Transaction>> findByAccountIban(String accountIban);
}
