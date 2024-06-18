package org.example.warehouse.stock.service.domain.dto.message;

import java.time.Instant;
import java.util.List;

public record StockTakeCreatedRequest(
        String id,
        String sagaId,
        Instant createdAt,
        Instant preparedDate,
        String stockTakeId,
        List<StockTakeItemRequest> items
) {
}
