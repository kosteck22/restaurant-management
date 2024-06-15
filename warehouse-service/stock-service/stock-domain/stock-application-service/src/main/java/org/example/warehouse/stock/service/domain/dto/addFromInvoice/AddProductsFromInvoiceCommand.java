package org.example.warehouse.stock.service.domain.dto.addFromInvoice;

import jakarta.validation.constraints.NotNull;
import org.example.domain.valueobject.InvoiceId;

public record AddProductsFromInvoiceCommand (
        @NotNull InvoiceId invoiceId
) {
}
