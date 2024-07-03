package org.example.warehouse.recipe.service.domain.dto.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public record CreateRecipeResponse(
        UUID recipeId,
        String message
) {
}
