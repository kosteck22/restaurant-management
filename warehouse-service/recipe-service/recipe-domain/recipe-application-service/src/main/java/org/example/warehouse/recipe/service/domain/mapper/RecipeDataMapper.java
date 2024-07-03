package org.example.warehouse.recipe.service.domain.mapper;

import org.example.domain.valueobject.MenuItemId;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.warehouse.recipe.service.domain.dto.create.CreateRecipeCommand;
import org.example.warehouse.recipe.service.domain.dto.create.RecipeItemCommand;
import org.example.warehouse.recipe.service.domain.entity.Recipe;
import org.example.warehouse.recipe.service.domain.entity.RecipeItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeDataMapper {
    public Recipe createRecipeCommandToRecipe(CreateRecipeCommand createRecipeCommand) {
        return Recipe.builder()
                .menuItemId(new MenuItemId(createRecipeCommand.menuItemId()))
                .items(recipeItemsCommandToRecipeItems(createRecipeCommand.items()))
                .build();
    }

    private List<RecipeItem> recipeItemsCommandToRecipeItems(List<RecipeItemCommand> items) {
        return items.stream()
                .map(recipeItemCommand -> RecipeItem.builder()
                        .productId(new ProductId(recipeItemCommand.productId()))
                        .quantity(new Quantity(recipeItemCommand.quantity()))
                        .build())
                .collect(Collectors.toList());
    }
}
