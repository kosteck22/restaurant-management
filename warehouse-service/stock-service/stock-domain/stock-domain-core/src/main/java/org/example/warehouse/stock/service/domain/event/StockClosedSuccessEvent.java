package org.example.warehouse.stock.service.domain.event;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.warehouse.stock.service.domain.entity.Stock;

import java.time.ZonedDateTime;

public class StockClosedSuccessEvent extends StockEvent {
    private final DomainEventPublisher<StockClosedSuccessEvent> stockClosedSuccessEventDomainEventPublisher;
    public StockClosedSuccessEvent(Stock stock, ZonedDateTime now, DomainEventPublisher<StockClosedSuccessEvent> stockClosedSuccessEventDomainEventPublisher) {
        super(stock, now);
        this.stockClosedSuccessEventDomainEventPublisher = stockClosedSuccessEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        stockClosedSuccessEventDomainEventPublisher.publish(this);
    }
}
