package org.example.sale.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.example.sale.service.domain.dto.complete.CompleteSaleResponse;
import org.example.sale.service.domain.dto.create.CreateSaleCommand;
import org.example.sale.service.domain.dto.create.CreateSaleResponse;
import org.example.sale.service.domain.ports.input.service.SaleApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@Service
public class SaleApplicationServiceImpl implements SaleApplicationService {
    private final SaleCreateCommandHandler saleCreateCommandHandler;
    private final SaleCompleteCommandHandler saleCompleteCommandHandler;

    public SaleApplicationServiceImpl(SaleCreateCommandHandler saleCreateCommandHandler,
                                      SaleCompleteCommandHandler saleCompleteCommandHandler) {
        this.saleCreateCommandHandler = saleCreateCommandHandler;
        this.saleCompleteCommandHandler = saleCompleteCommandHandler;
    }

    @Override
    public CreateSaleResponse createSale(CreateSaleCommand createSaleCommand) {
        return saleCreateCommandHandler.createSale(createSaleCommand);
    }

    @Override
    public CompleteSaleResponse completeSale(UUID saleId) {
        return saleCompleteCommandHandler.completeSale(saleId);
    }
}
