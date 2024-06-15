package org.example.warehouse.stock.take.service.domain.event;

import org.example.warehouse.stock.take.service.domain.entity.StockTake;

import java.time.ZonedDateTime;

public class StockTakeCreatedEvent extends StockTakeEvent {
    public StockTakeCreatedEvent(StockTake stockTake, ZonedDateTime createdAt) {
        super(stockTake, createdAt);
    }

    @Override
    public void fire() {
        //fire event
    }
}
