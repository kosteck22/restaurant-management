package org.example.invoice.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateInvoiceResponse(
        @NotNull UUID invoiceId,
        @NotNull String message
        ) {
}
