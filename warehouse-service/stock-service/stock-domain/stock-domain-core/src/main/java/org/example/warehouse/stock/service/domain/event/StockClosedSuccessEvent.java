package org.example.warehouse.stock.service.domain.event;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.domain.valueobject.StockTakeId;
import org.example.warehouse.stock.service.domain.entity.Stock;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class StockClosedSuccessEvent extends StockEvent {
    private final DomainEventPublisher<StockClosedSuccessEvent> stockClosedSuccessEventDomainEventPublisher;

    public StockClosedSuccessEvent(Stock stock, StockTakeId stockTakeId, List<String> failureMessages, ZonedDateTime createdAt, DomainEventPublisher<StockClosedSuccessEvent> stockClosedSuccessEventDomainEventPublisher) {
        super(stock, stockTakeId, failureMessages, createdAt);
        this.stockClosedSuccessEventDomainEventPublisher = stockClosedSuccessEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        stockClosedSuccessEventDomainEventPublisher.publish(this);
    }
}
