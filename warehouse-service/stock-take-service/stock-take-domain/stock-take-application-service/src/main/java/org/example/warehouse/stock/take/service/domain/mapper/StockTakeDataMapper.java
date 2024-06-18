package org.example.warehouse.stock.take.service.domain.mapper;

import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.warehouse.stock.take.service.domain.dto.create.CreateStockTakeCommand;
import org.example.warehouse.stock.take.service.domain.dto.create.CreateStockTakeResponse;
import org.example.warehouse.stock.take.service.domain.entity.Product;
import org.example.warehouse.stock.take.service.domain.entity.StockTakeItem;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockTakeDataMapper {

    public StockTake createStockTakeCommandToStockTake(CreateStockTakeCommand createStockTakeCommand) {
        return StockTake.builder()
                .preparedDate(createStockTakeCommand.preparedDate())
                .items(stockItemsToStockItemEntities(createStockTakeCommand.items()))
                .build();
    }

    private List<StockTakeItem> stockItemsToStockItemEntities(List<org.example.warehouse.stock.take.service.domain.dto.create.StockItem> items) {
        return items.stream()
                .map(stockItem -> StockTakeItem.builder()
                        .productId(new ProductId(stockItem.productId()))
                        .quantity(new Quantity(stockItem.quantity()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<Product> createStockTakeCommandToProducts(CreateStockTakeCommand createStockTakeCommand) {
        return createStockTakeCommand.items().stream()
                .map(stockItem -> Product.builder()
                        .id(new ProductId(stockItem.productId()))
                        .build())
                .collect(Collectors.toList());
    }

    public CreateStockTakeResponse stockTakeToCreateStockTakeResponse(StockTake stockTake, String message) {
        return new CreateStockTakeResponse(stockTake.getTrackingId().getValue(), stockTake.getStatus(), message);
    }
}
