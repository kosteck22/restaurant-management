package org.example.warehouse.stock.service.domain.event;

import org.example.warehouse.stock.service.domain.entity.Stock;

import java.time.ZonedDateTime;

public class ClosingStockSuccessfullEvent extends StockEvent {
    public ClosingStockSuccessfullEvent(Stock stock, ZonedDateTime now) {
        super(stock, now);
    }

    @Override
    public void fire() {

    }
}
