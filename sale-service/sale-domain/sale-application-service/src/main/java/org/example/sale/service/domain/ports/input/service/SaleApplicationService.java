package org.example.sale.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.example.sale.service.domain.dto.complete.CompleteSaleResponse;
import org.example.sale.service.domain.dto.create.CreateSaleCommand;
import org.example.sale.service.domain.dto.create.CreateSaleResponse;

import java.util.UUID;

public interface SaleApplicationService {
    CreateSaleResponse createSale(@Valid CreateSaleCommand createSaleCommand);

    CompleteSaleResponse completeSale(UUID saleId);
}
