package org.example.sale.service.domain.dto.create;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.application.validation.MonetaryValue;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleItem(
        @NotBlank UUID menuItemId,
        @NotNull @Min(value = 1) Integer quantity,
        @Range(min = 0, max = 100) Integer discount,
        @NotNull @MonetaryValue @DecimalMin(value = "0.01") BigDecimal grossPrice,
        @NotNull @MonetaryValue @DecimalMin(value = "0.01") BigDecimal grossPriceTotal
) {
}
