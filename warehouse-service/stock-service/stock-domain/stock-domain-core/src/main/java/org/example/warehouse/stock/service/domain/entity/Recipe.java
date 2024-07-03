package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.MenuItemId;
import org.example.domain.valueobject.RecipeId;

import java.util.List;

public class Recipe extends AggregateRoot<RecipeId> {
    private final MenuItemId menuItemId;
    private final List<RecipeItem> items;

    private Recipe(Builder builder) {
        setId(builder.recipeId);
        menuItemId = builder.menuItemId;
        items = builder.items;
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

    public static final class Builder {
        private RecipeId recipeId;
        private MenuItemId menuItemId;
        private List<RecipeItem> items;

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
            return new Recipe(this);
        }

        private Builder() {
        }
    }
}
