package org.example.warehouse.stock.service.domain.dto.message;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public record StockTakeCreatedRequest(
        String id,
        String sagaId,
        Instant createdAt,
        Instant preparedDate,
        String stockTakeId,
        List<StockTakeItem> items
) {
}
