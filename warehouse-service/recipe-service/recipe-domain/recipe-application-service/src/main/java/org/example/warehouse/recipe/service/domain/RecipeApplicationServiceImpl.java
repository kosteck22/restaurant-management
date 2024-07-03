package org.example.warehouse.recipe.service.domain;

import org.example.warehouse.recipe.service.domain.dto.create.CreateRecipeCommand;
import org.example.warehouse.recipe.service.domain.dto.create.CreateRecipeResponse;
import org.example.warehouse.recipe.service.domain.ports.input.service.RecipeApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class RecipeApplicationServiceImpl implements RecipeApplicationService {
    private final CreateRecipeCommandHandler createRecipeCommandHandler;

    public RecipeApplicationServiceImpl(CreateRecipeCommandHandler recipeCreateCommandHandler) {
        this.createRecipeCommandHandler = recipeCreateCommandHandler;
    }

    @Override
    public CreateRecipeResponse createRecipe(CreateRecipeCommand createRecipeCommand) {
        return createRecipeCommandHandler.createRecipe(createRecipeCommand);
    }
}
