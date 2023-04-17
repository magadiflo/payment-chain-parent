package com.magadiflo.ms.products.app.services;

import com.magadiflo.ms.products.app.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> listAll();

    Optional<Product> findById(Long id);

    Product save(Product Product);

    Optional<Product> edit(Long id, Product Product);

    Optional<Boolean> delete(Long id);
}
