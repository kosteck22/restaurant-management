package org.example.warehouse.recipe.service.domain.ports.output.repositorty;

import org.example.warehouse.recipe.service.domain.entity.Recipe;

public interface RecipeRepository {
    Recipe save(Recipe recipe);
}
