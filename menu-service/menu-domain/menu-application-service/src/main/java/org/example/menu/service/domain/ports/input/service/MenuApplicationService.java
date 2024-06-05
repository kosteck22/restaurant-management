package org.example.menu.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.example.menu.service.domain.dto.create.CreateMenuItemCommand;
import org.example.menu.service.domain.dto.create.CreateMenuItemResponse;

public interface MenuApplicationService {
    CreateMenuItemResponse createMenuItem(@Valid CreateMenuItemCommand createMenuItemCommand);
}
