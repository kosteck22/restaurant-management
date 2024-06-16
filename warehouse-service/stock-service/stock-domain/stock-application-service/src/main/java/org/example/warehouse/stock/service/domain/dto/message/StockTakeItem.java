package org.example.warehouse.stock.service.domain.dto.message;

import java.math.BigDecimal;
import java.util.UUID;

public record StockTakeItem(
        String stockTakeItemId,
        String name,
        BigDecimal quantity,
        String productId
) {
}
