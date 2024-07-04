package org.example.warehouse.stock.service.domain.ports.output.repository;

import org.example.warehouse.stock.service.domain.entity.Recipe;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository {
    List<Recipe> findAllByMenuItemIds(List<UUID> collect);
}
