package org.example.warehouse.stock.take.service.domain.dto.create;

import org.example.warehouse.stock.take.service.domain.valueobject.StockTakeStatus;

import java.util.UUID;

public record CreateStockTakeResponse(
        UUID stockTakeTrackingId,
        StockTakeStatus status,
        String message
) {
}
