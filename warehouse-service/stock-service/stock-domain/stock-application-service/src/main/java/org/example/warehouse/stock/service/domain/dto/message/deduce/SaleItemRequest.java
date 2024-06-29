package org.example.warehouse.stock.service.domain.dto.message.deduce;

import lombok.Builder;

@Builder
public record SaleItemRequest(
        String menuItemId,
        long quantity
) {
}
