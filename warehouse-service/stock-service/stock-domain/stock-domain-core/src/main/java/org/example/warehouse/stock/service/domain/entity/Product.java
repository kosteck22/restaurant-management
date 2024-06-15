package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.Category;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.UnitOfMeasure;

public class Product extends AggregateRoot<ProductId> {
    private final String name;
    private final Category category;
    private UnitOfMeasure unitOfMeasure;

    private Product(Builder builder) {
        setId(builder.productId);
        name = builder.name;
        unitOfMeasure = builder.unitOfMeasure;
        category = builder.category;
    }

    public void validateAndInitializeProduct() {

    }

    public Category getCategory() {
        return category;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductId productId;
        private String name;
        private Category category;
        private UnitOfMeasure unitOfMeasure;

        private Builder() {
        }

        public Builder productId(ProductId val) {
            productId = val;
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

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
