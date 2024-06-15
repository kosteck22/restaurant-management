package org.example.warehouse.stock.take.service.domain.event;

import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.domain.event.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;

public class StockTakeCreatedEvent extends StockTakeEvent {
    private final DomainEventPublisher<StockTakeCreatedEvent> stockTakeCreatedEventDomainEventPublisher;

    public StockTakeCreatedEvent(StockTake stockTake,
                                 ZonedDateTime createdAt,
                                 DomainEventPublisher<StockTakeCreatedEvent> stockTakeCreatedEventDomainEventPublisher) {
        super(stockTake, createdAt);
        this.stockTakeCreatedEventDomainEventPublisher = stockTakeCreatedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        stockTakeCreatedEventDomainEventPublisher.publish(this);
    }
}
