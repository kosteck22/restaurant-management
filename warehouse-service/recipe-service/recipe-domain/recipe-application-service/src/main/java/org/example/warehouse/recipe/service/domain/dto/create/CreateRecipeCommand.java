package org.example.warehouse.recipe.service.domain.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateRecipeCommand(
        @NotNull UUID menuItemId,
        @NotNull List<RecipeItemCommand> items
) {
}
