package org.example.warehouse.stock.take.service.messaging.mapper;

import org.example.domain.valueobject.StockTakeId;
import org.example.kafka.stock.take.avro.model.StockItem;
import org.example.kafka.stock.take.avro.model.StockTakeStatus;
import org.example.kafka.stock.take.avro.model.StockUpdateRequestAvroModel;
import org.example.kafka.stock.take.avro.model.StockUpdateResponseAvroModel;
import org.example.warehouse.stock.take.service.domain.dto.message.StockUpdateResponse;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.warehouse.stock.take.service.domain.entity.StockTakeItem;
import org.example.warehouse.stock.take.service.domain.event.StockTakeCreatedEvent;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StockTakeMessagingDataMapper {

    public StockUpdateRequestAvroModel stockTakeCreatedEventToStockUpdateRequestAvroModel(StockTakeCreatedEvent domainEvent) {
        StockTake stockTake = domainEvent.getStockTake();
        return StockUpdateRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setStockTakeId(stockTake.getId().getValue().toString())
                .setCreatedAt(domainEvent.getCreatedAt().toInstant())
                .setPreparedDate(stockTake.getPreparedDate().atStartOfDay().toInstant(ZoneOffset.UTC))
                .setStatus(StockTakeStatus.valueOf(stockTake.getStatus().toString()))
                .setItems(stockItemsToStockItemsAvroModel(stockTake.getItems(), stockTake.getId()))
                .build();
    }

    private List<StockItem> stockItemsToStockItemsAvroModel(List<StockTakeItem> items, StockTakeId stockTakeId) {
        return items.stream()
                .map(stockItem -> StockItem.newBuilder()
                        .setStockItemId(stockItem.getId().getValue())
                        .setStockTakeId(stockTakeId.getValue().toString())
                        .setName(stockItem.getName())
                        .setQuantity(stockItem.getQuantity().getValue())
                        .setProductId(stockItem.getProductId().getValue().toString())
                        .build())
                .collect(Collectors.toList());
    }

    public StockUpdateResponse stockUpdateResponseAvroModelToStockUpdateResponse(StockUpdateResponseAvroModel stockUpdateResponseAvroModel) {
        return new StockUpdateResponse(stockUpdateResponseAvroModel.getId(),
                stockUpdateResponseAvroModel.getSagaId(),
                stockUpdateResponseAvroModel.getStockTakeId(),
                stockUpdateResponseAvroModel.getStockId(),
                stockUpdateResponseAvroModel.getCreatedAt(),
                stockUpdateResponseAvroModel.getFailureMessages());
    }
}
