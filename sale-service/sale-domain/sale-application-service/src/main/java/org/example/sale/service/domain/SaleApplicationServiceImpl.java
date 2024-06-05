package org.example.sale.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.example.sale.service.domain.dto.create.CreateSaleCommand;
import org.example.sale.service.domain.dto.create.CreateSaleResponse;
import org.example.sale.service.domain.ports.input.service.SaleApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class SaleApplicationServiceImpl implements SaleApplicationService {
    private final SaleCreateCommandHandler saleCreateCommandHandler;

    public SaleApplicationServiceImpl(SaleCreateCommandHandler saleCreateCommandHandler) {
        this.saleCreateCommandHandler = saleCreateCommandHandler;
    }

    @Override
    public CreateSaleResponse createSale(CreateSaleCommand createSaleCommand) {
        return saleCreateCommandHandler.createSale(createSaleCommand);
    }
}
