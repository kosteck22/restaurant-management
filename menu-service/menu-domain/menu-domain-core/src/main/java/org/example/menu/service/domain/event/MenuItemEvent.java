package org.example.menu.service.domain.event;

import org.example.domain.event.DomainEvent;
import org.example.menu.service.domain.entity.MenuItem;

import java.time.ZonedDateTime;

public abstract class MenuItemEvent implements DomainEvent<MenuItem> {
    private final MenuItem menuItem;
    private final ZonedDateTime createdAt;

    public MenuItemEvent(MenuItem menuItem, ZonedDateTime createdAt) {
        this.menuItem = menuItem;
        this.createdAt = createdAt;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
