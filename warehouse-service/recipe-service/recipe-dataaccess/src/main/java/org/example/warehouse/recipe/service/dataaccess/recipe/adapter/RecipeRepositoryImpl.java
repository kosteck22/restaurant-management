package org.example.warehouse.recipe.service.dataaccess.recipe.adapter;

import org.example.warehouse.recipe.service.dataaccess.recipe.mapper.RecipeDataAccessMapper;
import org.example.dataaccess.recipe.repository.RecipeJpaRepository;
import org.example.warehouse.recipe.service.domain.entity.Recipe;
import org.example.warehouse.recipe.service.domain.ports.output.repositorty.RecipeRepository;
import org.springframework.stereotype.Component;

@Component
public class RecipeRepositoryImpl implements RecipeRepository {
    private final RecipeJpaRepository recipeJpaRepository;
    private final RecipeDataAccessMapper recipeDataAccessMapper;

    public RecipeRepositoryImpl(RecipeJpaRepository recipeJpaRepository,
                                RecipeDataAccessMapper recipeDataAccessMapper) {
        this.recipeJpaRepository = recipeJpaRepository;
        this.recipeDataAccessMapper = recipeDataAccessMapper;
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeDataAccessMapper.recipeEntityToRecipe(recipeJpaRepository
                .save(recipeDataAccessMapper.recipeToRecipeEntity(recipe)));
    }
}
