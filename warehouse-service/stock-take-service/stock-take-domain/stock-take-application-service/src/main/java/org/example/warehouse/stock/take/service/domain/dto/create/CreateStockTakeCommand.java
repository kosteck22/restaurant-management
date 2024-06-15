package org.example.warehouse.stock.take.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateStockTakeCommand(
        @NotNull LocalDateTime preparedDate,
        @NotNull List<StockItem> items
) {
}
