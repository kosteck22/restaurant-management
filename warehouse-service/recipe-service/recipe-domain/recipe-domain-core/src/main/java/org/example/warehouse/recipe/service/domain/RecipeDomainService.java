package org.example.warehouse.recipe.service.domain;

import org.example.domain.valueobject.ProductId;
import org.example.warehouse.recipe.service.domain.entity.Recipe;

import java.util.List;

public interface RecipeDomainService {
    void validateAndInitiate(Recipe recipe);
}
