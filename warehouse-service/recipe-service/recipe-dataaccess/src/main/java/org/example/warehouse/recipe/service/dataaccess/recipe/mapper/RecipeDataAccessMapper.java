package org.example.warehouse.recipe.service.dataaccess.recipe.mapper;

import org.example.domain.valueobject.*;
import org.example.warehouse.recipe.service.dataaccess.recipe.entity.RecipeEntity;
import org.example.warehouse.recipe.service.dataaccess.recipe.entity.RecipeItemEntity;
import org.example.warehouse.recipe.service.domain.entity.Recipe;
import org.example.warehouse.recipe.service.domain.entity.RecipeItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeDataAccessMapper {
    public RecipeEntity recipeToRecipeEntity(Recipe recipe) {
        RecipeEntity recipeEntity = RecipeEntity.builder()
                .id(recipe.getId().getValue())
                .menuItemId(recipe.getMenuItemId().getValue())
                .createdAt(recipe.getCreatedAt())
                .items(recipeItemsToRecipeItemEntities(recipe.getItems()))
                .build();
        recipeEntity.getItems().forEach(recipeItemEntity -> recipeItemEntity.setRecipe(recipeEntity));
        return recipeEntity;
    }

    private List<RecipeItemEntity> recipeItemsToRecipeItemEntities(List<RecipeItem> items) {
        return items.stream()
                .map(recipeItem -> RecipeItemEntity.builder()
                        .id(recipeItem.getId().getValue())
                        .productId(recipeItem.getProductId().getValue())
                        .quantity(recipeItem.getQuantity().getValue())
                        .build())
                .collect(Collectors.toList());
    }

    public Recipe recipeEntityToRecipe(RecipeEntity recipeEntity) {
        return Recipe.builder()
                .recipeId(new RecipeId(recipeEntity.getId()))
                .menuItemId(new MenuItemId(recipeEntity.getId()))
                .createdAt(recipeEntity.getCreatedAt())
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