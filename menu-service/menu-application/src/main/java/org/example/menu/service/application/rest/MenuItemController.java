package org.example.menu.service.application.rest;

import lombok.extern.slf4j.Slf4j;
import org.example.menu.service.domain.dto.create.CreateMenuItemCommand;
import org.example.menu.service.domain.dto.create.CreateMenuItemResponse;
import org.example.menu.service.domain.ports.input.service.MenuApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/menu-items", produces = "application/vnd.api.v1+json")
public class MenuItemController {

    private final MenuApplicationService menuApplicationService;

    public MenuItemController(MenuApplicationService menuApplicationService) {
        this.menuApplicationService = menuApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateMenuItemResponse> createMenuItem(@RequestBody CreateMenuItemCommand createMenuItemCommand) {
        log.info("Creating menu item");
        CreateMenuItemResponse createMenuItemResponse = menuApplicationService.createMenuItem(createMenuItemCommand);
        log.info("Menu item created with id: {}", createMenuItemResponse.menuItemId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createMenuItemResponse);
    }
}
