package org.example.sale.service.domain.dto.create;

import org.example.sale.service.domain.valueobject.SaleStatus;

import java.util.UUID;

public record CreateSaleResponse(
        UUID saleId,
        SaleStatus saleStatus,
        String message
) {
}
