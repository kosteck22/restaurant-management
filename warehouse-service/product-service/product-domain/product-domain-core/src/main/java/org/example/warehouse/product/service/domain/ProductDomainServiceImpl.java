package org.example.warehouse.product.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.product.service.domain.entity.Product;

@Slf4j
public class ProductDomainServiceImpl implements ProductDomainService {
    @Override
    public Product validateAndInitiateProduct(Product product) {
        product.initializeProduct();
        return product;
    }
}
