package org.example.warehouse.stock.service.domain.dto.service.add;

import java.util.UUID;

public record AddProductsFromInvoiceResponse (
        UUID StockId,
        String message
) {
}
