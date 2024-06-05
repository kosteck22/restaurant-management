package org.example.sale.service.dataaccess.menu.mapper;

import org.example.dataaccess.menu.entity.MenuItemEntity;
import org.example.domain.valueobject.MenuItemId;
import org.example.domain.valueobject.Money;
import org.example.sale.service.domain.entity.Menu;
import org.example.sale.service.domain.entity.MenuItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MenuDataAccessMapper {
    public Optional<Menu> menuItemEntitiesToOptionalMenu(List<MenuItemEntity> menuItemEntities) {
        if (menuItemEntities.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(Menu.builder()
                .items(menuItemEntities.stream()
                        .map(menuItemEntity -> MenuItem.builder()
                                .menuItemId(new MenuItemId(menuItemEntity.getId()))
                                .name(menuItemEntity.getName())
                                .grossPrice(new Money(menuItemEntity.getGrossPrice()))
                                .vatRate(menuItemEntity.getVatRate())
                                .active(menuItemEntity.isActive())
                                .build())
                        .toList())
                .build());
    }
}
