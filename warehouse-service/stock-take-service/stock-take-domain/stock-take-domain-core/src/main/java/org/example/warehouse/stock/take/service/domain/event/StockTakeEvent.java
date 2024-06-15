package org.example.warehouse.stock.take.service.domain.event;

import org.example.domain.event.DomainEvent;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;

import java.time.ZonedDateTime;

public abstract class StockTakeEvent implements DomainEvent<StockTake> {
    private final StockTake stockTake;
    private final ZonedDateTime createdAt;

    public StockTakeEvent(StockTake stockTake, ZonedDateTime createdAt) {
        this.stockTake = stockTake;
        this.createdAt = createdAt;
    }

    public StockTake getStockTake() {
        return stockTake;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
