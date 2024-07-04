package org.example.warehouse.stock.service.dataaccess.recipe.adapter;

import org.example.dataaccess.recipe.repository.RecipeJpaRepository;
import org.example.warehouse.stock.service.dataaccess.recipe.mapper.RecipeDataAccessMapper;
import org.example.warehouse.stock.service.domain.entity.Recipe;
import org.example.warehouse.stock.service.domain.ports.output.repository.RecipeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<Recipe> findAllByMenuItemIds(List<UUID> menuItemIds) {
        return recipeJpaRepository.findNewestRecipesByMenuItemIds(menuItemIds).stream()
                .map(recipeDataAccessMapper::recipeEntityToRecipe)
                .collect(Collectors.toList());
    }
}
