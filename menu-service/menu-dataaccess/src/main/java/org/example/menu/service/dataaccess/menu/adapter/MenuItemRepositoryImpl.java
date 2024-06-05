package org.example.menu.service.dataaccess.menu.adapter;

import org.example.dataaccess.menu.repository.MenuItemJpaRepository;
import org.example.menu.service.dataaccess.menu.mapper.MenuItemDataAccessMapper;
import org.example.menu.service.domain.entity.MenuItem;
import org.example.menu.service.domain.ports.output.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuItemRepositoryImpl implements MenuItemRepository {
    private final MenuItemJpaRepository menuItemJpaRepository;
    private final MenuItemDataAccessMapper menuItemDataAccessMapper;

    public MenuItemRepositoryImpl(MenuItemJpaRepository menuItemJpaRepository, MenuItemDataAccessMapper menuItemDataAccessMapper) {
        this.menuItemJpaRepository = menuItemJpaRepository;
        this.menuItemDataAccessMapper = menuItemDataAccessMapper;
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        return menuItemDataAccessMapper.menuItemEntityToMenuEntity(
                menuItemJpaRepository.save(
                        menuItemDataAccessMapper.menuItemToMenuItemEntity(menuItem)));
    }
}
