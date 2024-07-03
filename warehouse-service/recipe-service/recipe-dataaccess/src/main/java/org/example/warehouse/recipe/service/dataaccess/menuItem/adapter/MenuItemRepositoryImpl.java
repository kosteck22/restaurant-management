package org.example.warehouse.recipe.service.dataaccess.menuItem.adapter;

import org.example.dataaccess.menu.repository.MenuItemJpaRepository;
import org.example.warehouse.recipe.service.dataaccess.menuItem.mapper.MenuItemDataAccessMapper;
import org.example.warehouse.recipe.service.domain.entity.MenuItem;
import org.example.warehouse.recipe.service.domain.ports.output.repositorty.MenuItemRepository;

import java.util.Optional;
import java.util.UUID;

public class MenuItemRepositoryImpl implements MenuItemRepository {
    private final MenuItemJpaRepository menuItemJpaRepository;
    private final MenuItemDataAccessMapper menuItemDataAccessMapper;

    public MenuItemRepositoryImpl(MenuItemJpaRepository menuItemJpaRepository,
                                  MenuItemDataAccessMapper menuItemDataAccessMapper) {
        this.menuItemJpaRepository = menuItemJpaRepository;
        this.menuItemDataAccessMapper = menuItemDataAccessMapper;
    }

    @Override
    public Optional<MenuItem> findMenuItem(UUID menuItemId) {
        return menuItemJpaRepository.findById(menuItemId).map(menuItemDataAccessMapper::menuItemEntityToMenuItem);
    }
}
