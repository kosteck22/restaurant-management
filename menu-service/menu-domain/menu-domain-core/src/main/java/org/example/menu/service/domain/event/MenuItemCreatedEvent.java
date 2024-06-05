package org.example.menu.service.domain.event;

import org.example.menu.service.domain.entity.MenuItem;

import java.time.ZonedDateTime;

public class MenuItemCreatedEvent extends MenuItemEvent {
    public MenuItemCreatedEvent(MenuItem menuItem, ZonedDateTime createdAt) {
        super(menuItem, createdAt);
    }

    @Override
    public void fire() {
        //publish event
    }
}
