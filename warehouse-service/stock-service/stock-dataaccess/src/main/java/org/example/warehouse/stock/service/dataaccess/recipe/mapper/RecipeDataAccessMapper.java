package org.example.warehouse.stock.service.dataaccess.recipe.mapper;

import org.example.dataaccess.recipe.entity.RecipeEntity;
import org.example.dataaccess.recipe.entity.RecipeItemEntity;
import org.example.domain.valueobject.MenuItemId;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.domain.valueobject.RecipeItemId;
import org.example.warehouse.stock.service.domain.entity.Recipe;
import org.example.warehouse.stock.service.domain.entity.RecipeItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeDataAccessMapper {
    public Recipe recipeEntityToRecipe(RecipeEntity recipeEntity) {
        return Recipe.builder()
                .menuItemId(new MenuItemId(recipeEntity.getMenuItemId()))
                .items(recipeItemEntitiesToRecipeItems(recipeEntity.getItems()))
                .build();
    }

    private List<RecipeItem> recipeItemEntitiesToRecipeItems(List<RecipeItemEntity> items) {
        return items.stream()
                .map(recipeItemEntity -> RecipeItem.builder()
                        .recipeItemId(new RecipeItemId(recipeItemEntity.getId()))
                        .productId(new ProductId(recipeItemEntity.getProductId()))
                        .quantity(new Quantity(recipeItemEntity.getQuantity()))
                        .build())
                .collect(Collectors.toList());
    }
}
