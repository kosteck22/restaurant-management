package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.domain.valueobject.RecipeItemId;

public class RecipeItem extends BaseEntity<RecipeItemId> {
    private final ProductId productId;
    private final Quantity quantity;

    private RecipeItem(Builder builder) {
        setId(builder.recipeItemId);
        productId = builder.productId;
        quantity = builder.quantity;
    }

    public ProductId getProductId() {
        return productId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private RecipeItemId recipeItemId;
        private ProductId productId;
        private Quantity quantity;

        private Builder() {
        }

        public Builder recipeItemId(RecipeItemId val) {
            recipeItemId = val;
            return this;
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder quantity(Quantity val) {
            quantity = val;
            return this;
        }

        public RecipeItem build() {
            return new RecipeItem(this);
        }
    }
}
