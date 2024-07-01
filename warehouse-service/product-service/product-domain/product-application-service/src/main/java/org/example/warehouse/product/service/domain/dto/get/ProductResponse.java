package org.example.warehouse.product.service.domain.dto.get;

import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String name,
        String category
) {
}
