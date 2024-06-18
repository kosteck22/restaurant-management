package org.example.warehouse.stock.service.domain.event;

import org.example.domain.event.DomainEvent;
import org.example.warehouse.stock.service.domain.entity.Stock;

import java.time.ZonedDateTime;

public abstract class StockEvent implements DomainEvent<Stock> {
    private final Stock stock;
    private final ZonedDateTime createdAt;

    public StockEvent(Stock stock, ZonedDateTime createdAt) {
        this.stock = stock;
        this.createdAt = createdAt;
    }

    public Stock getStock() {
        return stock;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
