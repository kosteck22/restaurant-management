package org.example.warehouse.recipe.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.example.warehouse.recipe.service.domain.dto.create.CreateRecipeCommand;
import org.example.warehouse.recipe.service.domain.dto.create.CreateRecipeResponse;

public interface RecipeApplicationService {
    CreateRecipeResponse createRecipe(@Valid CreateRecipeCommand createRecipeCommand);
}
