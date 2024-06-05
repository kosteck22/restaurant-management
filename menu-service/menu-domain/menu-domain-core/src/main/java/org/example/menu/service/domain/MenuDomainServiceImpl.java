package org.example.menu.service.domain;

import static org.example.domain.DomainConstants.UTC;

import lombok.extern.slf4j.Slf4j;
import org.example.menu.service.domain.entity.MenuItem;
import org.example.menu.service.domain.event.MenuItemCreatedEvent;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class MenuDomainServiceImpl implements MenuDomainService {

    @Override
    public MenuItemCreatedEvent validateAndInitiate(MenuItem menuItem) {
        menuItem.validateMenuItem();
        menuItem.initializeMenuItem();
        log.info("Menu item with id: {} is initiated", menuItem.getId().getValue());
        return new MenuItemCreatedEvent(menuItem, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}
