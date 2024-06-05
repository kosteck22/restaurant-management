package org.example.menu.service.domain.mapper;

import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.VatRate;
import org.example.menu.service.domain.dto.create.CreateMenuItemCommand;
import org.example.menu.service.domain.entity.Category;
import org.example.menu.service.domain.entity.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemDataMapper {
    public MenuItem createMenuItemCommandToMenuItem(CreateMenuItemCommand createMenuItemCommand) {
        return MenuItem.builder()
                .name(createMenuItemCommand.name())
                .shortName(createMenuItemCommand.shortName())
                .description(createMenuItemCommand.description())
                .vatRate(VatRate.valueOf(createMenuItemCommand.vatRate()))
                .grossPrice(new Money(createMenuItemCommand.grossPrice()))
                .active(createMenuItemCommand.active())
                .category(Category.builder()
                        .name(createMenuItemCommand.category())
                        .build())
                .build();
    }
}
