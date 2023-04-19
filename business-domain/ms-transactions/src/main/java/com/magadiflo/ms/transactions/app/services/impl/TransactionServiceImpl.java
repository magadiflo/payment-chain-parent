package com.magadiflo.ms.transactions.app.services.impl;

import com.magadiflo.ms.transactions.app.entities.Transaction;
import com.magadiflo.ms.transactions.app.repositories.ITransactionRepository;
import com.magadiflo.ms.transactions.app.services.ITransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository transactionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> listAll() {
        return this.transactionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaction> findById(Long id) {
        return this.transactionRepository.findById(id);
    }

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Optional<Transaction> edit(Long id, Transaction transaction) {
        return this.transactionRepository.findById(id)
                .map(transactionBD -> {
                    transactionBD.setAmount(transaction.getAmount());
                    transactionBD.setChannel(transaction.getChannel());
                    transactionBD.setDate(transaction.getDate());
                    transactionBD.setDescripcion(transaction.getDescripcion());
                    transactionBD.setFee(transaction.getFee());
                    transactionBD.setAccountIban(transaction.getAccountIban());
                    transactionBD.setReference(transaction.getReference());
                    transactionBD.setStatus(transaction.getStatus());
                    return Optional.of(this.transactionRepository.save(transactionBD));
                })
                .orElseGet(Optional::empty);
    }

    @Override
    @Transactional
    public Optional<Boolean> delete(Long id) {
        return this.transactionRepository.findById(id)
                .map(transactionBD -> {
                    this.transactionRepository.deleteById(id);
                    return Optional.of(true);
                })
                .orElseGet(Optional::empty);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Transaction>> findByAccountIban(String accountIban) {
        return this.transactionRepository.findByAccountIban(accountIban);
    }
}
