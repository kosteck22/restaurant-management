package org.example.warehouse.stock.service.domain.event;

import org.example.domain.event.DomainEvent;
import org.example.domain.valueobject.StockTakeId;
import org.example.warehouse.stock.service.domain.entity.Stock;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public abstract class StockEvent implements DomainEvent<Stock> {
    private final Stock stock;
    private final StockTakeId stockTakeId;
    private final List<String> failureMessages;
    private final ZonedDateTime createdAt;

    public StockEvent(Stock stock, StockTakeId stockTakeId, List<String> failureMessages, ZonedDateTime createdAt) {
        this.stock = stock;
        this.stockTakeId = stockTakeId;
        this.failureMessages = failureMessages;
        this.createdAt = createdAt;
    }

    public Stock getStock() {
        return stock;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public StockTakeId getStockTakeId() {
        return stockTakeId;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }
}
