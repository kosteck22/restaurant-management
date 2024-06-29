package org.example.sale.service.domain.ports.output.message.publisher;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.sale.service.domain.event.SalePaidEvent;

public interface SalePaidEventMessagePublisher extends DomainEventPublisher<SalePaidEvent> {
}
