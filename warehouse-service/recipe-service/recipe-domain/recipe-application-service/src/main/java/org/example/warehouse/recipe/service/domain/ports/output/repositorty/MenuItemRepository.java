package org.example.warehouse.recipe.service.domain.ports.output.repositorty;

import org.example.warehouse.recipe.service.domain.entity.MenuItem;

import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository {
    Optional<MenuItem> findMenuItem(UUID menuItemId);
}
