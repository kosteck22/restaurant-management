package org.example.warehouse.stock.service.domain.mapper;

import org.example.domain.valueobject.*;
import org.example.warehouse.stock.service.domain.dto.message.deduce.StockSubtractRequest;
import org.example.warehouse.stock.service.domain.dto.message.update.StockUpdateRequest;
import org.example.warehouse.stock.service.domain.dto.message.update.StockTakeItemRequest;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceResponse;
import org.example.warehouse.stock.service.domain.entity.Sale;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.entity.StockTake;
import org.example.warehouse.stock.service.domain.entity.StockTakeItem;
import org.example.warehouse.stock.service.domain.valueobject.SaleItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.example.domain.DomainConstants.UTC;

@Component
public class StockDataMapper {
    public StockTake stockUpdatedRequestToStockTake(StockUpdateRequest stockTakeCreatedRequest) {
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

    public AddProductsFromInvoiceResponse stockToAddProductsFromInvoiceResponse(Stock stock, String message) {
        return new AddProductsFromInvoiceResponse(stock.getId().getValue(), message);
    }

    public Sale stockSubtractRequestToSale(StockSubtractRequest stockSubtractRequest) {
        return Sale.builder()
                .id(new SaleId(UUID.fromString(stockSubtractRequest.saleId())))
                .date(LocalDateTime.ofInstant(stockSubtractRequest.date(), ZoneId.of(UTC)))
                .items(stockSubtractRequest.items().stream()
                        .map(saleItemRequest -> SaleItem.builder()
                                .menuItemId(new MenuItemId(UUID.fromString(saleItemRequest.menuItemId())))
                                .quantity(new Quantity(BigDecimal.valueOf(saleItemRequest.quantity())))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
