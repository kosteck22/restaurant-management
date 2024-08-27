package org.example.warehouse.stock.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceCommand;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceResponse;
import org.example.warehouse.stock.service.domain.ports.input.service.StockApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;


@Slf4j
@Validated
@Service
public class StockApplicationServiceImpl implements StockApplicationService {
    private final AddProductsFromInvoiceCommandHandler addProductsFromInvoiceCommandHandler;
    private final FetchMethodsCommandHandler fetchMethodsCommandHandler;

    public StockApplicationServiceImpl(AddProductsFromInvoiceCommandHandler addProductsFromInvoiceCommandHandler, FetchMethodsCommandHandler costOfGoodsSoldCommandHandler) {
        this.addProductsFromInvoiceCommandHandler = addProductsFromInvoiceCommandHandler;
        this.fetchMethodsCommandHandler = costOfGoodsSoldCommandHandler;
    }

    @Override
    public AddProductsFromInvoiceResponse addProductsFromInvoice(AddProductsFromInvoiceCommand addProductsFromInvoiceCommand) {
        return addProductsFromInvoiceCommandHandler.addProductsFromInvoice(addProductsFromInvoiceCommand);
    }

    @Override
    public BigDecimal getCostOfGoodsSold() {
        return fetchMethodsCommandHandler.getCostOfGoodsSold();
    }

    @Override
    public Map<UUID, BigDecimal> getVariance() {
        return fetchMethodsCommandHandler.getVariance();
    }
}
