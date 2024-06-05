package org.example.menu.service.domain.dto.create;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.application.validation.MonetaryValue;

import java.math.BigDecimal;

public record CreateMenuItemCommand(
        @NotBlank String name,
        @NotBlank String shortName,
        String description,
        @NotBlank String category,
        @NotNull Integer vatRate,
        @NotNull @MonetaryValue
        @DecimalMin(value = "0.01", message = "Price must be greater than 0!")
        BigDecimal grossPrice,
        boolean active
) {
}
