package org.example.warehouse.stock.service.domain.dto.message;

import java.math.BigDecimal;

public record StockTakeItemRequest(
        String stockTakeItemId,
        String name,
        BigDecimal quantity,
        String productId
) {
}
