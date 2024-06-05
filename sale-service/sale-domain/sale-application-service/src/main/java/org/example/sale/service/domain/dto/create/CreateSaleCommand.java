package org.example.sale.service.domain.dto.create;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.example.application.validation.MonetaryValue;

import java.math.BigDecimal;
import java.util.List;

public record CreateSaleCommand(
        @NotNull @MonetaryValue @DecimalMin(value = "0.01") BigDecimal grossPrice,
        @NotNull List<SaleItem> items
) {
}
