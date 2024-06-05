package org.example.menu.service.domain.dto.create;

import java.util.UUID;

public record CreateMenuItemResponse(
        UUID menuItemId,
        String message
) {
}
