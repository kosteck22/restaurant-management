package org.example.invoice.service.domain.dto.create;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.example.application.validation.MonetaryValue;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Builder
public record OrderItem(
        @NotNull String productName,
        @NotNull @MonetaryValue BigDecimal netPrice,
        @NotNull Integer vatRate,
        @NotNull String unitOfMeasure,
        @NotNull @DecimalMin(value = "0.001", message = "Quantity must be greater than 0") BigDecimal quantity,
        @Range(min = 0, max = 100) Integer discount
) {
}
