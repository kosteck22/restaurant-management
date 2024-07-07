package org.example.waregouse.stock.service.messaging.mapper;

import org.example.kafka.stock.sale.avro.model.StockSubtractRequestAvroModel;
import org.example.kafka.stock.take.avro.model.StockUpdateRequestAvroModel;
import org.example.kafka.stock.take.avro.model.StockUpdateResponseAvroModel;
import org.example.warehouse.stock.service.domain.dto.message.deduce.SaleItemRequest;
import org.example.warehouse.stock.service.domain.dto.message.deduce.StockSubtractRequest;
import org.example.warehouse.stock.service.domain.dto.message.update.StockUpdateRequest;
import org.example.warehouse.stock.service.domain.dto.message.update.StockTakeItemRequest;
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

    public StockUpdateResponseAvroModel stockClosedFailedEventToStockUpdateResponseAvroModel(StockClosedSuccessEvent domainEvent) {
        return StockEventToStockUpdateResponseAvroModel(domainEvent);
    }

    public StockUpdateResponseAvroModel stockClosedFailedEventToStockUpdateResponseAvroModel(StockClosedFailedEvent domainEvent) {
        return StockEventToStockUpdateResponseAvroModel(domainEvent);
    }

    private StockUpdateResponseAvroModel StockEventToStockUpdateResponseAvroModel(StockEvent domainEvent) {
        return StockUpdateResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setStockTakeId(domainEvent.getStockTakeId().getValue().toString())
                .setStockId(domainEvent.getStock().getId().getValue().toString())
                .setCreatedAt(domainEvent.getCreatedAt().toInstant())
                .setFailureMessages(domainEvent.getFailureMessages())
                .build();
    }

    public StockSubtractRequest stockSubtractRequestAvroModelToStockSubtractRequest(StockSubtractRequestAvroModel stockSubtractRequestAvroModel) {
        return StockSubtractRequest.builder()
                .id(stockSubtractRequestAvroModel.getId())
                .sagaId("")
                .createdAt(stockSubtractRequestAvroModel.getCreatedAt())
                .date(stockSubtractRequestAvroModel.getDate())
                .saleId(stockSubtractRequestAvroModel.getSaleId())
                .items(stockSubtractRequestAvroModel.getItems().stream()
                        .map(saleItemAvro -> SaleItemRequest.builder()
                                .menuItemId(saleItemAvro.getMenuItemId())
                                .quantity(saleItemAvro.getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
