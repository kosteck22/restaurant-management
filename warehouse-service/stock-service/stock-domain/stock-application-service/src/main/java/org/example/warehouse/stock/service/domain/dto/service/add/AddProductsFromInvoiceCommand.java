package org.example.warehouse.stock.service.domain.dto.service.add;

import jakarta.validation.constraints.NotNull;
import org.example.domain.valueobject.InvoiceId;

import java.util.UUID;

public record AddProductsFromInvoiceCommand (
        @NotNull UUID invoiceId
) {
}
