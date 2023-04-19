package com.magadiflo.ms.customers.app.resources;

import com.magadiflo.ms.customers.app.entities.Customer;
import com.magadiflo.ms.customers.app.services.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v0/customers")
public class CustomerResource {

    private final ICustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers() {
        return ResponseEntity.ok(this.customerService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Customer> showCustomer(@PathVariable Long id) {
        return this.customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.customerService.save(customer));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Customer> editCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return this.customerService.edit(id, customer)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        return this.customerService.delete(id)
                .map(value -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/full")
    public ResponseEntity<Customer> getByCode(@RequestParam String code) {
        return this.customerService.findByCode(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
