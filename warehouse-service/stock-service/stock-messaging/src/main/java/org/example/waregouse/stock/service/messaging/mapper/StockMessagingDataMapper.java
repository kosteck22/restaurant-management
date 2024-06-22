package org.example.waregouse.stock.service.messaging.mapper;

import org.example.kafka.stock.take.avro.model.StockUpdateRequestAvroModel;
import org.example.kafka.stock.take.avro.model.StockUpdateResponseAvroModel;
import org.example.warehouse.stock.service.domain.dto.message.StockUpdateRequest;
import org.example.warehouse.stock.service.domain.dto.message.StockTakeItemRequest;
import org.example.warehouse.stock.service.domain.event.StockClosedFailedEvent;
import org.example.warehouse.stock.service.domain.event.StockClosedSuccessEvent;
import org.example.warehouse.stock.service.domain.event.StockEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StockMessagingDataMapper {
    public StockUpdateRequest stockUpdateRequestAvroModelToStockTakeCreatedRequest(StockUpdateRequestAvroModel stockUpdateRequestAvroModel) {
        return new StockUpdateRequest(
                stockUpdateRequestAvroModel.getId(),
                stockUpdateRequestAvroModel.getSagaId(),
                stockUpdateRequestAvroModel.getCreatedAt(),
                stockUpdateRequestAvroModel.getPreparedDate(),
                stockUpdateRequestAvroModel.getStockTakeId(),
                stockUpdateRequestAvroModel.getItems().stream()
                        .map(stockItem -> new StockTakeItemRequest(
                                stockItem.getStockTakeId(),
                                stockItem.getName(),
                                stockItem.getQuantity(),
                                stockItem.getProductId()
                        ))
                        .collect(Collectors.toList())
        );
    }

    public StockUpdateResponseAvroModel stockClosedSuccessEventToStockUpdateResponseAvroModel(StockClosedSuccessEvent domainEvent) {
        return StockEventToStockUpdateResponseAvroModel(domainEvent);
    }

    public StockUpdateResponseAvroModel stockClosedSuccessEventToStockUpdateResponseAvroModel(StockClosedFailedEvent domainEvent) {
        return StockEventToStockUpdateResponseAvroModel(domainEvent);
    }

    private StockUpdateResponseAvroModel StockEventToStockUpdateResponseAvroModel(StockEvent domainEvent) {
        return StockUpdateResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setStockTakeId(domainEvent.getStockTakeId().toString())
                .setStockId(domainEvent.getStock().getId().toString())
                .setCreatedAt(domainEvent.getCreatedAt().toInstant())
                .setFailureMessages(domainEvent.getFailureMessages())
                .build();
    }
}
