package org.example.warehouse.stock.take.service.domain.dto.message;

import java.time.Instant;
import java.util.List;

public record StockUpdateResponse (
        String id,
        String sagaId,
        String stockTakeId,
        String stockId,
        Instant createdAt,
        List<String>failureMessages
) {
}
