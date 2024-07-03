package org.example.warehouse.recipe.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.recipe.service.domain.entity.Recipe;

@Slf4j
public class RecipeDomainServiceImpl implements RecipeDomainService {

    @Override
    public void validateAndInitiate(Recipe recipe) {
        recipe.validateRecipe();
        recipe.initializeRecipe();
        log.info("Recipe with id: {} is initiated", recipe.getId().getValue());
    }
}
