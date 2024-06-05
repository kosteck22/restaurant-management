package org.example.menu.service.dataaccess.menu.mapper;

import org.example.domain.valueobject.MenuItemId;
import org.example.domain.valueobject.Money;
import org.example.dataaccess.menu.category.entity.CategoryEntity;
import org.example.dataaccess.menu.entity.MenuItemEntity;
import org.example.menu.service.domain.entity.Category;
import org.example.menu.service.domain.entity.MenuItem;
import org.example.menu.service.domain.valueobject.CategoryId;
import org.springframework.stereotype.Component;

@Component
public class MenuItemDataAccessMapper {
    public MenuItem menuItemEntityToMenuEntity(MenuItemEntity menuItem) {
        return MenuItem.builder()
                .menuItemId(new MenuItemId(menuItem.getId()))
                .name(menuItem.getName())
                .shortName(menuItem.getShortName())
                .description(menuItem.getDescription())
                .vatRate(menuItem.getVatRate())
                .grossPrice(new Money(menuItem.getGrossPrice()))
                .active(menuItem.isActive())
                .category(categoryEntityToCategory(menuItem.getCategory()))
                .build();
    }

    private Category categoryEntityToCategory(CategoryEntity category) {
        return Category.builder()
                .categoryId(new CategoryId(category.getId()))
                .name(category.getName())
                .build();
    }

    public MenuItemEntity menuItemToMenuItemEntity(MenuItem menuItem) {
        return MenuItemEntity.builder()
                .id(menuItem.getId().getValue())
                .name(menuItem.getName())
                .shortName(menuItem.getShortName())
                .description(menuItem.getDescription())
                .vatRate(menuItem.getVatRate())
                .grossPrice(menuItem.getGrossPrice().getAmount())
                .active(menuItem.isActive())
                .category(categoryToCategoryEntity(menuItem.getCategory()))
                .build();
    }

    private CategoryEntity categoryToCategoryEntity(Category category) {
        return CategoryEntity.builder()
                .id(category.getId().getValue())
                .name(category.getName())
                .build();
    }
}
