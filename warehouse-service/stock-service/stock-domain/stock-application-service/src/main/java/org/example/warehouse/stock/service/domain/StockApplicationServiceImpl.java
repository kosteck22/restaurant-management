package org.example.warehouse.stock.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceCommand;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceResponse;
import org.example.warehouse.stock.service.domain.ports.input.service.StockApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;


@Slf4j
@Validated
@Service
public class StockApplicationServiceImpl implements StockApplicationService {
    private final AddProductsFromInvoiceCommandHandler addProductsFromInvoiceCommandHandler;
    private final CostOfGoodsSoldCommandHandler costOfGoodsSoldCommandHandler;

    public StockApplicationServiceImpl(AddProductsFromInvoiceCommandHandler addProductsFromInvoiceCommandHandler, CostOfGoodsSoldCommandHandler costOfGoodsSoldCommandHandler) {
        this.addProductsFromInvoiceCommandHandler = addProductsFromInvoiceCommandHandler;
        this.costOfGoodsSoldCommandHandler = costOfGoodsSoldCommandHandler;
    }

    @Override
    public AddProductsFromInvoiceResponse addProductsFromInvoice(AddProductsFromInvoiceCommand addProductsFromInvoiceCommand) {
        return addProductsFromInvoiceCommandHandler.addProductsFromInvoice(addProductsFromInvoiceCommand);
    }

    @Override
    public BigDecimal getCostOfGoodsSold() {
        return costOfGoodsSoldCommandHandler.getCostOfGoodsSold();
    }
}
