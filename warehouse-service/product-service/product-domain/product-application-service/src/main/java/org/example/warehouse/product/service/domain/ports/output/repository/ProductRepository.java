package org.example.warehouse.product.service.domain.ports.output.repository;

import org.example.warehouse.product.service.domain.entity.Product;
import org.example.warehouse.product.service.domain.valueobject.ProductId;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(ProductId saleId);
}
