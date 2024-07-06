package org.example.sale.service.domain.mapper;

import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.Quantity;
import org.example.sale.service.domain.dto.complete.CompleteSaleResponse;
import org.example.sale.service.domain.dto.create.CreateSaleCommand;
import org.example.sale.service.domain.dto.create.CreateSaleResponse;
import org.example.sale.service.domain.entity.Menu;
import org.example.sale.service.domain.entity.MenuItem;
import org.example.sale.service.domain.entity.Sale;
import org.example.sale.service.domain.entity.SaleItem;
import org.example.domain.valueobject.MenuItemId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaleDataMapper {
    public Sale CreateSaleCommandToSale(CreateSaleCommand createSaleCommand) {
        return Sale.builder()
                .grossPrice(new Money(createSaleCommand.grossPrice()))
                .items(saleItemsToSaleItemEntities(createSaleCommand.items()))
                .build();
    }

    private List<SaleItem> saleItemsToSaleItemEntities(List<org.example.sale.service.domain.dto.create.SaleItem> items) {
        return items.stream()
                .map(saleItem -> SaleItem.builder()
                        .quantity(new Quantity(BigDecimal.valueOf(saleItem.quantity())))
                        .discount(saleItem.discount() == null ? 0 : saleItem.discount())
                        .grossPrice(new Money(saleItem.grossPrice()))
                        .grossPriceTotal(new Money(saleItem.grossPriceTotal()))
                        .menuItem(new MenuItem(new MenuItemId(saleItem.menuItemId())))
                        .build())
                .collect(Collectors.toList());
    }

    public Menu createSaleCommandToMenu(CreateSaleCommand createSaleCommand) {
        return Menu.builder()
                .items(createSaleCommand.items().stream()
                        .map(saleItem -> new MenuItem(new MenuItemId(saleItem.menuItemId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public CreateSaleResponse saleToCreateSaleResponse(Sale sale, String message) {
        return new CreateSaleResponse(sale.getId().getValue(), sale.getSaleStatus(), message);
    }

    public CompleteSaleResponse saleToCompleteSaleResponse(Sale sale, String message) {
        return new CompleteSaleResponse(sale.getId().getValue(), sale.getSaleStatus(), message);
    }
}
