package org.example.sale.service.domain.dto.create;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleItem(
        UUID menuItemId,
        Integer quantity,
        Integer discount,
        BigDecimal grossPrice,
        BigDecimal grossPriceTotal
) {
}
