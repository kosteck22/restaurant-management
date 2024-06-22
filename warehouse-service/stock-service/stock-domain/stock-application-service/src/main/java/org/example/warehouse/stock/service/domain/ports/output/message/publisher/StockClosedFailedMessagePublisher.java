package org.example.warehouse.stock.service.domain.ports.output.message.publisher;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.warehouse.stock.service.domain.event.StockClosedFailedEvent;

public interface StockClosedFailedMessagePublisher extends DomainEventPublisher<StockClosedFailedEvent> {
}
