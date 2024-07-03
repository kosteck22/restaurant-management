package org.example.warehouse.recipe.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.MenuItemId;

public class MenuItem extends AggregateRoot<MenuItemId> {
    public MenuItem() {
    }

    public MenuItem(MenuItemId menuItemId) {
        super.setId(menuItemId);
    }
}
