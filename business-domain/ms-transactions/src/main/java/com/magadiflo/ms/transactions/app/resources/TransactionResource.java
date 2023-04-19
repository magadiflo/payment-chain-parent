package com.magadiflo.ms.transactions.app.resources;

import com.magadiflo.ms.transactions.app.entities.Transaction;
import com.magadiflo.ms.transactions.app.services.ITransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v0/transactions")
public class TransactionResource {

    private final ITransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> listAllTransactions() {
        return ResponseEntity.ok(this.transactionService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Transaction> showTransaction(@PathVariable Long id) {
        return this.transactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.transactionService.save(transaction));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Transaction> editTransaction(@PathVariable Long id, @RequestBody Transaction customer) {
        return this.transactionService.edit(id, customer)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        return this.transactionService.delete(id)
                .map(value -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/transactions-of-client")
    public ResponseEntity<List<Transaction>> getByAccountIban(@RequestParam String accountIban) {
        return this.transactionService.findByAccountIban(accountIban)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
