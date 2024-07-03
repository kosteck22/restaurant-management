package org.example.warehouse.recipe.service.dataaccess.menuItem.mapper;

import org.example.dataaccess.menu.entity.MenuItemEntity;
import org.example.domain.valueobject.MenuItemId;
import org.example.warehouse.recipe.service.domain.entity.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemDataAccessMapper {
    public MenuItem menuItemEntityToMenuItem(MenuItemEntity menuItemEntity) {
        return new MenuItem(new MenuItemId(menuItemEntity.getId()));
    }
}
