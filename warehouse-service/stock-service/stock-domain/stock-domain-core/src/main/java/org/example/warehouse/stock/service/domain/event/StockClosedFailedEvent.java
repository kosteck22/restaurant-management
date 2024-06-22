package org.example.warehouse.stock.service.domain.event;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.domain.valueobject.StockTakeId;
import org.example.warehouse.stock.service.domain.entity.Stock;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class StockClosedFailedEvent extends StockEvent {
    private final DomainEventPublisher<StockClosedFailedEvent> stockClosedFailedEventDomainEventPublisher;

    public StockClosedFailedEvent(Stock stock, StockTakeId stockTakeId, List<String> failureMessages, ZonedDateTime createdAt, DomainEventPublisher<StockClosedFailedEvent> stockClosedFailedEventDomainEventPublisher) {
        super(stock, stockTakeId, failureMessages, createdAt);
        this.stockClosedFailedEventDomainEventPublisher = stockClosedFailedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        stockClosedFailedEventDomainEventPublisher.publish(this);
    }
}
