package org.example.sale.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.sale.service.domain.entity.Menu;
import org.example.sale.service.domain.entity.MenuItem;
import org.example.sale.service.domain.entity.Sale;
import org.example.sale.service.domain.event.SaleCreatedEvent;
import org.example.sale.service.domain.event.SalePaidEvent;
import org.example.sale.service.domain.exception.SaleDomainException;
import org.example.domain.valueobject.MenuItemId;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.example.domain.DomainConstants.UTC;

@Slf4j
public class SaleDomainServiceImpl implements SaleDomainService {
    @Override
    public SaleCreatedEvent validateAndInitiateSale(Sale sale, Menu menu) {
        setSaleMenuItemInformation(sale, menu);
        sale.validateSale();
        sale.initializeSale();
        log.info("Sale with id: {} is initiated", sale.getId().getValue());
        return new SaleCreatedEvent(sale, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public SalePaidEvent paySale(Sale sale) {
        return null;
    }

    @Override
    public void cancelSale(Sale sale, List<String> failureMessages) {

    }

    private void setSaleMenuItemInformation(Sale sale, Menu menu) {
        Map<MenuItemId, MenuItem> menuItemMapFromMenu = menu.getItems().stream().
                collect(Collectors.toMap(MenuItem::getId, Function.identity()));
        sale.getItems().forEach(saleItem -> {
            MenuItem currentMenuItem = saleItem.getMenuItem();
            MenuItem menuItem = menuItemMapFromMenu.get(currentMenuItem.getId());
            if (menuItem == null) {
                log.info("Menu item not found for id: {}", currentMenuItem.getId().getValue());
                throw new SaleDomainException("Menu item not found for id " + currentMenuItem.getId().getValue());
            }
            currentMenuItem.updateWithConfirmedData(
                    menuItem.getName(),
                    menuItem.getGrossPrice(),
                    menuItem.getVatRate(),
                    menuItem.isActive());
        });
    }
}
