package org.example.warehouse.product.service.domain;

import org.example.warehouse.product.service.domain.entity.Product;

public interface ProductDomainService {

    Product validateAndInitiateProduct(Product product);
}
