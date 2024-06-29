package org.example.warehouse.stock.service.domain.dto.message.deduce;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record StockSubtractRequest(
        String id,
        String sagaId,
        Instant createdAt,
        Instant date,
        String saleId,
        List<SaleItemRequest> items
) {
}
