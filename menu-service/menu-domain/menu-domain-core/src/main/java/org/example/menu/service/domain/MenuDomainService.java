package org.example.menu.service.domain;

import org.example.menu.service.domain.entity.MenuItem;
import org.example.menu.service.domain.event.MenuItemCreatedEvent;

public interface MenuDomainService {
    MenuItemCreatedEvent validateAndInitiate(MenuItem menuItem);
}
