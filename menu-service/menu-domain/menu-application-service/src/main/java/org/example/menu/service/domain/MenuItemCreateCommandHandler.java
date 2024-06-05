package org.example.menu.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.menu.service.domain.dto.create.CreateMenuItemCommand;
import org.example.menu.service.domain.dto.create.CreateMenuItemResponse;
import org.example.menu.service.domain.entity.Category;
import org.example.menu.service.domain.entity.MenuItem;
import org.example.menu.service.domain.event.MenuItemCreatedEvent;
import org.example.menu.service.domain.exception.MenuItemDomainException;
import org.example.menu.service.domain.mapper.MenuItemDataMapper;
import org.example.menu.service.domain.ports.output.repository.CategoryRepository;
import org.example.menu.service.domain.ports.output.repository.MenuItemRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class MenuItemCreateCommandHandler {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemDataMapper menuItemDataMapper;
    private final MenuDomainService menuDomainService;
    private final CategoryRepository categoryRepository;

    public MenuItemCreateCommandHandler(MenuItemRepository menuItemRepository,
                                        MenuItemDataMapper menuItemDataMapper,
                                        MenuDomainService menuDomainService,
                                        CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemDataMapper = menuItemDataMapper;
        this.menuDomainService = menuDomainService;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CreateMenuItemResponse createMenuItem(CreateMenuItemCommand createMenuItemCommand) {
        MenuItem menuItem = menuItemDataMapper.createMenuItemCommandToMenuItem(createMenuItemCommand);
        Optional<Category> optionalCategory = categoryRepository.findByName(createMenuItemCommand.category());
        optionalCategory.ifPresent(category -> menuItem.getCategory().setId(category.getId()));
        MenuItemCreatedEvent menuItemCreatedEvent = menuDomainService.validateAndInitiate(menuItem);
        saveMenuItem(menuItem);
        return new CreateMenuItemResponse(menuItemCreatedEvent.getMenuItem().getId().getValue(), "Menu Item successfully created!");
    }

    private MenuItem saveMenuItem(MenuItem menuItem) {
        MenuItem menuItemResult = menuItemRepository.save(menuItem);
        if (menuItemResult == null) {
            log.error("Could not save menu item!");
            throw new MenuItemDomainException("Could not save menu item!");
        }
        log.info("Menu item is saved with id: {}", menuItemResult.getId().getValue());
        return menuItemResult;
    }
}
