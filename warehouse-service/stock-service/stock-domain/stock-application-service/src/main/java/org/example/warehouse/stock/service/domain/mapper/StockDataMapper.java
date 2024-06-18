package org.example.warehouse.stock.service.domain.mapper;

import org.example.domain.DomainConstants;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.domain.valueobject.StockTakeId;
import org.example.domain.valueobject.StockTakeItemId;
import org.example.warehouse.stock.service.domain.dto.message.StockTakeCreatedRequest;
import org.example.warehouse.stock.service.domain.dto.message.StockTakeItemRequest;
import org.example.warehouse.stock.service.domain.entity.StockTake;
import org.example.warehouse.stock.service.domain.entity.StockTakeItem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.example.domain.DomainConstants.UTC;

@Component
public class StockDataMapper {
    public StockTake stockTakeCreatedRequestToStockTake(StockTakeCreatedRequest stockTakeCreatedRequest) {
        return StockTake.builder()
                .id(new StockTakeId(UUID.fromString(stockTakeCreatedRequest.stockTakeId())))
                .preparedDate(LocalDateTime.ofInstant(stockTakeCreatedRequest.preparedDate(), ZoneId.of(UTC)))
                .items(stockTakeItemsRequestToStockTakeItems(stockTakeCreatedRequest.items()))
                .build();
    }

    private List<StockTakeItem> stockTakeItemsRequestToStockTakeItems(List<StockTakeItemRequest> items) {
        return items.stream()
                .map(stockTakeItemRequest -> StockTakeItem.builder()
                        .id(new StockTakeItemId(Long.getLong(stockTakeItemRequest.stockTakeItemId())))
                        .productId(new ProductId(UUID.fromString(stockTakeItemRequest.productId())))
                        .quantity(new Quantity(stockTakeItemRequest.quantity()))
                        .build())
                .collect(Collectors.toList());
    }
}
