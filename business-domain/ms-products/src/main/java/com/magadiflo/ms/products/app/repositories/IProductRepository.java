package com.magadiflo.ms.products.app.repositories;

import com.magadiflo.ms.products.app.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
}
