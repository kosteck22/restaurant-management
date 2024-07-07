package org.example.warehouse.recipe.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.MenuItemId;
import org.example.domain.valueobject.RecipeId;
import org.example.domain.valueobject.RecipeItemId;
import org.example.warehouse.recipe.service.domain.exception.RecipeDomainException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Recipe extends AggregateRoot<RecipeId> {
    private LocalDateTime createdAt;
    private final MenuItemId menuItemId;
    private final List<RecipeItem> items;


    private Recipe(LocalDateTime createdAt, Builder builder) {
        this.createdAt = createdAt;
        setId(builder.recipeId);
        menuItemId = builder.menuItemId;
        items = builder.items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public MenuItemId getMenuItemId() {
        return menuItemId;
    }

    public List<RecipeItem> getItems() {
        return items;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void validateRecipe() {
        if (getId() != null) {
            throw new RecipeDomainException("Recipe is not in correct state for initialization!");
        }
        validateRecipeItemQuantities();
    }

    private void validateRecipeItemQuantities() {
        items.forEach(recipeItem -> {
            if (recipeItem.getQuantity().isNegative()) {
                throw new RecipeDomainException("Quantity for product id: %s is negative".formatted(recipeItem.getProductId().getValue()));
            }
        });
    }

    public void initializeRecipe() {
        setId(new RecipeId(UUID.randomUUID()));
        initializeRecipeItems();
        createdAt = LocalDateTime.now();
    }

    private void initializeRecipeItems() {
        long itemId = 1;
        for (RecipeItem recipeItem: items) {
            recipeItem.initializeRecipeItem(super.getId(), new RecipeItemId(itemId++));
        }
    }

    public static final class Builder {
        private LocalDateTime createdAt;
        private RecipeId recipeId;
        private MenuItemId menuItemId;
        private List<RecipeItem> items;

        public Builder createdAt(LocalDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder recipeId(RecipeId val) {
            recipeId = val;
            return this;
        }

        public Builder menuItemId(MenuItemId val) {
            menuItemId = val;
            return this;
        }

        public Builder items(List<RecipeItem> val) {
            items = val;
            return this;
        }

        public Recipe build() {
            return new Recipe(createdAt, this);
        }

        private Builder() {
        }
    }
}
