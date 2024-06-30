package org.example.warehouse.stock.service.dataaccess.recipe.adapter;

import org.example.warehouse.stock.service.domain.entity.Recipe;
import org.example.warehouse.stock.service.domain.ports.output.repository.RecipeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RecipeRepositoryImpl implements RecipeRepository {

    @Override
    public List<Recipe> findAllById(List<UUID> collect) {
        return null;
    }
}
