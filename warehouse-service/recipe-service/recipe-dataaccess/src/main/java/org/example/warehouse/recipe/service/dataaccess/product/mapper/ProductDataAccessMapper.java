package org.example.warehouse.recipe.service.dataaccess.product.mapper;

import org.example.dataaccess.product.entity.ProductEntity;
import org.example.domain.valueobject.ProductId;
import org.example.warehouse.recipe.service.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDataAccessMapper {
    public Product productEntityToProduct(ProductEntity productEntity) {
        return new Product(new ProductId(productEntity.getId()));
    }
}