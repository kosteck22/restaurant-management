package org.example.sale.service.dataaccess.menu.adapter;

import org.example.dataaccess.menu.entity.MenuItemEntity;
import org.example.dataaccess.menu.repository.MenuItemJpaRepository;
import org.example.domain.valueobject.MenuItemId;
import org.example.domain.valueobject.Money;
import org.example.sale.service.dataaccess.menu.mapper.MenuDataAccessMapper;
import org.example.sale.service.domain.entity.Menu;
import org.example.sale.service.domain.entity.MenuItem;
import org.example.sale.service.domain.ports.output.repository.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MenuRepositoryImpl implements MenuRepository {
    private final MenuItemJpaRepository menuItemJpaRepository;
    private final MenuDataAccessMapper menuDataAccessMapper;

    public MenuRepositoryImpl(MenuItemJpaRepository menuItemJpaRepository,
                              MenuDataAccessMapper menuDataAccessMapper) {
        this.menuItemJpaRepository = menuItemJpaRepository;
        this.menuDataAccessMapper = menuDataAccessMapper;
    }

    @Override
    public Optional<Menu> findMenuInformation(Menu menu) {
        List<UUID> menuItemIds = menu.getItems().stream()
                .map(menuItem -> menuItem.getId().getValue())
                .toList();
        return menuDataAccessMapper.menuItemEntitiesToOptionalMenu(
                menuItemJpaRepository.findAllById(menuItemIds));
    }
}
