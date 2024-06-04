package org.example.sale.service.domain.dto.create;

import java.math.BigDecimal;
import java.util.List;

public record CreateSaleCommand(
        BigDecimal grossPrice,
        List<SaleItem> items
) {
}
