package org.example.warehouse.stock.take.service.dataaccess.stockTake.mapper;

import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.warehouse.stock.take.service.dataaccess.stockTake.entity.StockTakeItemEntity;
import org.example.warehouse.stock.take.service.dataaccess.stockTake.entity.StockTakeEntity;
import org.example.warehouse.stock.take.service.domain.entity.StockTakeItem;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.domain.valueobject.StockTakeItemId;
import org.example.domain.valueobject.StockTakeId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockTakeDataAccessMapper {
    public StockTakeEntity stockTakeToStockTakeEntity(StockTake stockTake) {
        StockTakeEntity stockTakeEntity = StockTakeEntity.builder()
                .id(stockTake.getId().getValue())
                .preparedDate(stockTake.getPreparedDate())
                .totalPrice(stockTake.getTotalPrice().getAmount())
                .items(stockItemsToStockItemEntities(stockTake.getItems()))
                .build();
        stockTakeEntity.getItems().forEach(stockTakeItemEntity -> stockTakeItemEntity.setStockTake(stockTakeEntity));
        return stockTakeEntity;
    }

    private List<StockTakeItemEntity> stockItemsToStockItemEntities(List<StockTakeItem> items) {
        return items.stream()
                .map(stockItem -> StockTakeItemEntity.builder()
                        .id(stockItem.getId().getValue())
                        .productId(stockItem.getProductId().getValue())
                        . name(stockItem.getName())
                        .quantity(stockItem.getQuantity().getValue())
                        .totalPrice(stockItem.getTotalGrossPrice().getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    public StockTake stockTakeEntityToStockTake(StockTakeEntity stockTake) {
        return StockTake.builder()
                .stockTakeId(new StockTakeId(stockTake.getId()))
                .preparedDate(stockTake.getPreparedDate())
                .totalPrice(new Money(stockTake.getTotalPrice()))
                .items(stockTakeItemEntitiesToStockTakeItems(stockTake.getItems()))
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
