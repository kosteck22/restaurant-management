package org.example.warehouse.stock.take.service.messaging.mapper;

import org.example.kafka.stock.take.avro.model.StockItem;
import org.example.kafka.stock.take.avro.model.StockTakeAvroModel;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.warehouse.stock.take.service.domain.event.StockTakeCreatedEvent;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.example.domain.DomainConstants.UTC;

@Component
public class StockTakeMessagingDataMapper {

    public StockTakeAvroModel stockTakeCreatedEventToStockTakeAvroModel(StockTakeCreatedEvent domainEvent) {
        StockTake stockTake = domainEvent.getStockTake();
        StockTakeAvroModel stockTakeAvroModel = StockTakeAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCreatedAt(domainEvent.getCreatedAt().toInstant())
                .setPreparedDate(stockTake.getPreparedDate().toInstant(ZoneOffset.of(UTC)))
                .setStockTakeId(stockTake.getId().toString())
                .setItems(stockItemsToStockItemsAvroModel(stockTake.getItems()))
                .build();
        stockTakeAvroModel.getItems()
                .forEach(stockItem -> stockItem.setStockTakeId(stockTake.getId().getValue().toString()));
        return stockTakeAvroModel;
    }

    private List<StockItem> stockItemsToStockItemsAvroModel(List<org.example.warehouse.stock.take.service.domain.entity.StockItem> items) {
        return items.stream()
                .map(stockItem -> StockItem.newBuilder()
                        .setStockItemId(stockItem.getId().getValue())
                        .setName(stockItem.getName())
                        .setQuantity(stockItem.getQuantity().getValue())
                        .setProductId(stockItem.getProductId().getValue().toString())
                        .build())
                .collect(Collectors.toList());
    }
}