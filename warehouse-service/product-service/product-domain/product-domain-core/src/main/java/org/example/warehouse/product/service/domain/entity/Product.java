package org.example.warehouse.product.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.UnitOfMeasure;
import org.example.domain.valueobject.Category;
import org.example.domain.valueobject.ProductId;

import java.util.UUID;

public class Product extends AggregateRoot<ProductId> {
    private String name;
    private Category category;
    private UnitOfMeasure unitOfMeasure;

    private Product(Builder builder) {
        setId(builder.productId);
        name = builder.name;
        category = builder.category;
        unitOfMeasure = builder.unitOfMeasure;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeProduct() {
        setId(new ProductId(UUID.randomUUID()));
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public static final class Builder {
        private ProductId productId;
        private String name;
        private Category category;
        private UnitOfMeasure unitOfMeasure;

        private Builder() {
        }

        public Builder id(ProductId val) {
            productId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder category(Category val) {
            category = val;
            return this;
        }

        public Builder unitOfMeasure(UnitOfMeasure val) {
            unitOfMeasure = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
