package org.example.warehouse.stock.take.service.domain.ports.output.message.publisher;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.warehouse.stock.take.service.domain.event.StockTakeCreatedEvent;

public interface StockTakeCreatedMessagePublisher extends DomainEventPublisher<StockTakeCreatedEvent> {
}
