package org.example.warehouse.stock.service.domain.event;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.warehouse.stock.service.domain.entity.Stock;

import java.time.ZonedDateTime;

public class StockClosedFailedEvent extends StockEvent {
    private final DomainEventPublisher<StockClosedFailedEvent> stockClosedFailedEventDomainEventPublisher;

    public StockClosedFailedEvent(Stock stock, ZonedDateTime now, DomainEventPublisher<StockClosedFailedEvent> stockClosedFailedEventDomainEventPublisher) {
        super(stock, now);
        this.stockClosedFailedEventDomainEventPublisher = stockClosedFailedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        stockClosedFailedEventDomainEventPublisher.publish(this);
    }
}
