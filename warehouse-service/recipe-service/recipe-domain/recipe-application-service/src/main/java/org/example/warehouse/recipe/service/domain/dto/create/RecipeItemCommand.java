package org.example.warehouse.recipe.service.domain.dto.create;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record RecipeItemCommand(
        @NotNull UUID productId,
        @NotNull  @DecimalMin(value = "0.01") BigDecimal quantity
) {
}
