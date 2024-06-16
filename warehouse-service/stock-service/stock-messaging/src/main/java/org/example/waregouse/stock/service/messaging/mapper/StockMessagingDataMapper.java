package org.example.waregouse.stock.service.messaging.mapper;

import org.example.kafka.stock.take.avro.model.StockTakeAvroModel;
import org.example.warehouse.stock.service.domain.dto.message.StockTakeCreatedRequest;
import org.example.warehouse.stock.service.domain.dto.message.StockTakeItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StockMessagingDataMapper {
    public StockTakeCreatedRequest stockTakeAvroModelToStockTakeCreatedRequest(StockTakeAvroModel stockTakeAvroModel) {
        return new StockTakeCreatedRequest(
                stockTakeAvroModel.getId(),
                stockTakeAvroModel.getSagaId(),
                stockTakeAvroModel.getCreatedAt(),
                stockTakeAvroModel.getPreparedDate(),
                stockTakeAvroModel.getStockTakeId(),
                stockTakeAvroModel.getItems().stream()
                        .map(stockItem -> new StockTakeItem(
                                stockItem.getStockTakeId(),
                                stockItem.getName(),
                                stockItem.getQuantity(),
                                stockItem.getProductId()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
