package org.example.menu.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.menu.service.domain.dto.create.CreateMenuItemCommand;
import org.example.menu.service.domain.dto.create.CreateMenuItemResponse;
import org.example.menu.service.domain.ports.input.service.MenuApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class MenuApplicationServiceImpl implements MenuApplicationService {

    private final MenuItemCreateCommandHandler createMenuItemCommandHandler;

    public MenuApplicationServiceImpl(MenuItemCreateCommandHandler createMenuItemCommandHandler) {
        this.createMenuItemCommandHandler = createMenuItemCommandHandler;
    }

    @Override
    public CreateMenuItemResponse createMenuItem(CreateMenuItemCommand createMenuItemCommand) {
        return createMenuItemCommandHandler.createMenuItem(createMenuItemCommand);
    }
}
