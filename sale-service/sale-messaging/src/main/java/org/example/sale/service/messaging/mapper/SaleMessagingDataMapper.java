package org.example.sale.service.messaging.mapper;

import org.example.kafka.stock.sale.avro.model.SaleItem;
import org.example.kafka.stock.sale.avro.model.SaleStatus;
import org.example.kafka.stock.sale.avro.model.StockDeduceRequestAvroModel;
import org.example.sale.service.domain.event.SalePaidEvent;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SaleMessagingDataMapper {
    public StockDeduceRequestAvroModel SalePaidEventToStockDeduceRequestAvroModel(SalePaidEvent domainEvent) {
        return StockDeduceRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setSaleId(domainEvent.getSale().getId().toString())
                .setCreatedAt(domainEvent.getCreatedAt().toInstant())
                .setSaleStatus(SaleStatus.PAID)
                .setDate(domainEvent.getSale().getDate().toInstant(ZoneOffset.UTC))
                .setItems(domainEvent.getSale().getItems().stream()
                        .map(saleItem -> SaleItem.newBuilder()
                                .setMenuItemId(saleItem.getMenuItem().getId().toString())
                                .setQuantity(saleItem.getQuantity().getValue().longValue())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
