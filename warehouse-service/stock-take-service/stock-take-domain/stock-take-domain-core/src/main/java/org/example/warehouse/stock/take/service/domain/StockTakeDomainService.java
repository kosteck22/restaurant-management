package org.example.warehouse.stock.take.service.domain;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.warehouse.stock.take.service.domain.entity.Product;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.warehouse.stock.take.service.domain.event.StockTakeCreatedEvent;

import java.util.List;

public interface StockTakeDomainService {
    StockTakeCreatedEvent validateAndInitiateStockTake(StockTake stockTake, List<Product> products, DomainEventPublisher<StockTakeCreatedEvent> stockTakeCreatedEventDomainEventPublisher);
}
