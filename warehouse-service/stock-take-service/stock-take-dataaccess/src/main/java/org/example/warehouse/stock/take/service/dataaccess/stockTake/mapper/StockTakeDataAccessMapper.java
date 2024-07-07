package org.example.warehouse.stock.take.service.dataaccess.stockTake.mapper;

import org.example.domain.valueobject.*;
import org.example.warehouse.stock.take.service.dataaccess.stockTake.entity.StockTakeItemEntity;
import org.example.warehouse.stock.take.service.dataaccess.stockTake.entity.StockTakeEntity;
import org.example.warehouse.stock.take.service.domain.entity.StockTakeItem;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.domain.DomainConstants.FAILURE_MESSAGE_DELIMITER;

@Component
public class StockTakeDataAccessMapper {
    public StockTakeEntity stockTakeToStockTakeEntity(StockTake stockTake) {
        StockTakeEntity stockTakeEntity = StockTakeEntity.builder()
                .id(stockTake.getId().getValue())
                .preparedDate(stockTake.getPreparedDate())
                .status(stockTake.getStatus())
                .totalPrice(stockTake.getTotalPrice() == null ? null : stockTake.getTotalPrice().getAmount())
                .items(stockItemsToStockItemEntities(stockTake.getItems()))
                .trackingId(stockTake.getTrackingId().getValue())
                .failureMessages(stockTake.getFailureMessages() != null ?
                        String.join(FAILURE_MESSAGE_DELIMITER, stockTake.getFailureMessages()) : "")
                .build();
        stockTakeEntity.getItems().forEach(stockTakeItemEntity -> stockTakeItemEntity.setStockTake(stockTakeEntity));
        return stockTakeEntity;
    }

    private List<StockTakeItemEntity> stockItemsToStockItemEntities(List<StockTakeItem> items) {
        return items.stream()
                .map(stockItem -> StockTakeItemEntity.builder()
                        .id(stockItem.getId().getValue())
                        .productId(stockItem.getProductId().getValue())
                        .name(stockItem.getName() == null ? null : stockItem.getName())
                        .quantity(stockItem.getQuantity().getValue())
                        .totalPrice(stockItem.getTotalGrossPrice() == null ? null : stockItem.getTotalGrossPrice().getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    public StockTake stockTakeEntityToStockTake(StockTakeEntity stockTakeEntity) {
        return StockTake.builder()
                .stockTakeId(new StockTakeId(stockTakeEntity.getId()))
                .preparedDate(stockTakeEntity.getPreparedDate())
                .stockTakeStatus(stockTakeEntity.getStatus())
                .totalPrice(new Money(stockTakeEntity.getTotalPrice()))
                .trackingId(new TrackingId(stockTakeEntity.getTrackingId()))
                .failureMessages(stockTakeEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(stockTakeEntity.getFailureMessages()
                                .split(FAILURE_MESSAGE_DELIMITER))))
                .items(stockTakeItemEntitiesToStockTakeItems(stockTakeEntity.getItems()))
                .build();
    }

    private List<StockTakeItem> stockTakeItemEntitiesToStockTakeItems(List<StockTakeItemEntity> items) {
        return items.stream()
                .map(stockTakeItemEntity -> StockTakeItem.builder()
                        .stockTakeItemId(new StockTakeItemId(stockTakeItemEntity.getId()))
                        .stockTakeId(new StockTakeId(stockTakeItemEntity.getStockTake().getId()))
                        .productId(new ProductId(stockTakeItemEntity.getProductId()))
                        .name(stockTakeItemEntity.getName())
                        .quantity(new Quantity(stockTakeItemEntity.getQuantity()))
                        .totalPrice(new Money(stockTakeItemEntity.getTotalPrice()))
                        .build())
                .collect(Collectors.toList());
    }
}
