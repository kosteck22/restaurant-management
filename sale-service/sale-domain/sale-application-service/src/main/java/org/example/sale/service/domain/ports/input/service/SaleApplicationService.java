package org.example.sale.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.example.sale.service.domain.dto.create.CreateSaleCommand;
import org.example.sale.service.domain.dto.create.CreateSaleResponse;

public interface SaleApplicationService {
    CreateSaleResponse createSale(@Valid CreateSaleCommand createSaleCommand);
}
