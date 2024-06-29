package org.example.sale.service.domain.event;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.sale.service.domain.entity.Sale;

import java.time.ZonedDateTime;

public class SalePaidEvent extends SaleEvent {
    private final DomainEventPublisher<SalePaidEvent> stockTakeCreatedEventDomainEventPublisher;

    public SalePaidEvent(Sale sale,
                         ZonedDateTime createdAt,
                         DomainEventPublisher<SalePaidEvent> stockTakeCreatedEventDomainEventPublisher) {
        super(sale, createdAt);
        this.stockTakeCreatedEventDomainEventPublisher = stockTakeCreatedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        stockTakeCreatedEventDomainEventPublisher.publish(this);
    }
}
