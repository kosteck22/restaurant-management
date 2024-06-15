package org.example.warehouse.stock.service.domain.dto.addFromInvoice;

import java.util.UUID;

public record AddProductsFromInvoiceResponse (
        UUID StockId,
        String message
) {
}
