package org.example.warehouse.stock.take.service.domain.dto.create;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record StockItem (
        @NotNull UUID productId,
        @NotNull @DecimalMin(value = "0.001", message = "Quantity must be greater than 0") BigDecimal quantity
) {
}
