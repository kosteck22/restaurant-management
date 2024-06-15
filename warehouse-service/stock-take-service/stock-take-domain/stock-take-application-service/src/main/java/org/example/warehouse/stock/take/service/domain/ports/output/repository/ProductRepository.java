package org.example.warehouse.stock.take.service.domain.ports.output.repository;

import org.example.domain.valueobject.ProductId;
import org.example.warehouse.stock.take.service.domain.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findProductsInformation(List<Product> products);
}
