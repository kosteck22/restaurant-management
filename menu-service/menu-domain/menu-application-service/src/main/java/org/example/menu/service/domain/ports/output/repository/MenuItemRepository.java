package org.example.menu.service.domain.ports.output.repository;

import org.example.menu.service.domain.entity.MenuItem;

public interface MenuItemRepository {
    MenuItem save(MenuItem menuItem);
}
