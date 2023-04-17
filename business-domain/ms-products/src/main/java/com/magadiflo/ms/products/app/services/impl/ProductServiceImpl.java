package com.magadiflo.ms.products.app.services.impl;

import com.magadiflo.ms.products.app.entities.Product;
import com.magadiflo.ms.products.app.repositories.IProductRepository;
import com.magadiflo.ms.products.app.services.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> listAll() {
        return this.productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Optional<Product> edit(Long id, Product product) {
        return this.productRepository.findById(id)
                .map(productBD -> {
                    productBD.setCode(product.getCode());
                    productBD.setName(product.getName());
                    return Optional.of(this.productRepository.save(productBD));
                })
                .orElseGet(Optional::empty);
    }

    @Override
    @Transactional
    public Optional<Boolean> delete(Long id) {
        return this.productRepository.findById(id)
                .map(productBD -> {
                    this.productRepository.deleteById(productBD.getId());
                    return Optional.of(true);
                })
                .orElseGet(Optional::empty);
    }
}
