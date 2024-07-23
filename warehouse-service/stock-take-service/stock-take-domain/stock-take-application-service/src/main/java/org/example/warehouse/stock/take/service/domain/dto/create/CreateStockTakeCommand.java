package org.example.warehouse.stock.take.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CreateStockTakeCommand(
        @NotNull LocalDate preparedDate,
        @NotNull List<StockItem> items
) {
}
