package org.example.warehouse.stock.service.domain.ports.output.repository;

import org.example.warehouse.stock.service.domain.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    List<Product> saveAll(List<Product> products);
}
