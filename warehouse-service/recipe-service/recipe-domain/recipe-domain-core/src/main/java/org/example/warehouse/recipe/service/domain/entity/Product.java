package org.example.warehouse.recipe.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.ProductId;

public class Product extends AggregateRoot<ProductId> {
    public Product() {
    }

    public Product(ProductId productId) {
        super.setId(productId);
    }
}
