package org.example.sale.service.domain.dto.complete;

import org.example.sale.service.domain.valueobject.SaleStatus;

import java.util.UUID;

public record CompleteSaleResponse (
        UUID saleId,
        SaleStatus saleStatus,
        String message
) {
}
