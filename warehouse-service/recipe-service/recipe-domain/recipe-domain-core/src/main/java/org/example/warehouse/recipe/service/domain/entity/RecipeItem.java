package org.example.warehouse.recipe.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.*;

import java.util.List;

public class RecipeItem extends BaseEntity<RecipeItemId> {
    private RecipeId recipeId;
    private final ProductId productId;
    private final Quantity quantity;


    private RecipeItem(Builder builder) {
        setId(builder.recipeItemId);
        productId = builder.productId;
        quantity = builder.quantity;
    }

    public RecipeId getRecipeId() {
        return recipeId;
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

    public void initializeRecipeItem(RecipeId id, RecipeItemId recipeItemId) {
        this.recipeId = id;
        super.setId(recipeItemId);
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
