package org.example.warehouse.stock.service.domain.event;

import org.example.warehouse.stock.service.domain.entity.Stock;

import java.time.ZonedDateTime;

public class ClosingStockFailedEvent extends StockEvent {
    public ClosingStockFailedEvent(Stock stock, ZonedDateTime now) {
        super(stock, now);
    }

    @Override
    public void fire() {
        //
    }
}
