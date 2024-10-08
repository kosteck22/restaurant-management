package org.example.warehouse.stock.take.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.domain.valueobject.ProductId;
import org.example.warehouse.stock.take.service.domain.entity.Product;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.warehouse.stock.take.service.domain.event.StockTakeCreatedEvent;
import org.example.warehouse.stock.take.service.domain.exception.StockTakeDomainException;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.example.domain.DomainConstants.UTC;

@Slf4j
public class StockTakeDomainServiceImpl implements StockTakeDomainService {

    @Override
    public StockTakeCreatedEvent validateAndInitiateStockTake(StockTake stockTake,
                                                              List<Product> products,
                                                              DomainEventPublisher<StockTakeCreatedEvent> stockTakeCreatedEventDomainEventPublisher) {
        setStockItemInformation(stockTake, products);
        stockTake.validateStockTake();
        stockTake.initializeStockTake();
        log.info("Stock take with id: {} is initiated", stockTake.getId().getValue());
        return new StockTakeCreatedEvent(stockTake, ZonedDateTime.now(ZoneOffset.UTC), stockTakeCreatedEventDomainEventPublisher);

    }

    @Override
    public void acceptStockTake(StockTake stockTake) {
        stockTake.accept();
        log.info("Stock take with id: {} is accepted", stockTake.getId().getValue());
    }

    @Override
    public void rejectStockTake(StockTake stockTake, List<String> failureMessages) {
        stockTake.reject(failureMessages);
        log.info("Stock take with id: {} is rejected", stockTake.getId().getValue());
    }

    private void setStockItemInformation(StockTake stockTake, List<Product> products) {
        Map<ProductId, Product> productIdToProductMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        stockTake.getItems().forEach(stockItem -> {
            Product product = productIdToProductMap.get(stockItem.getProductId());
            if (product == null) {
                log.info("Product with id: {} not found", stockItem.getProductId());
                throw new StockTakeDomainException("Product with id %s not found".formatted(stockItem.getProductId()));
            }
            stockItem.updateName(product.getName());
        });
    }
}
