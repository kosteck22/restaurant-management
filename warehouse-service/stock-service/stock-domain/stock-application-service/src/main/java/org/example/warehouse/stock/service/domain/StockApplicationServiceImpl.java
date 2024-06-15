package org.example.warehouse.stock.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.dto.addFromInvoice.AddProductsFromInvoiceCommand;
import org.example.warehouse.stock.service.domain.dto.addFromInvoice.AddProductsFromInvoiceResponse;
import org.example.warehouse.stock.service.domain.ports.input.service.StockApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Slf4j
@Validated
@Service
public class StockApplicationServiceImpl implements StockApplicationService {
    private final AddProductsFromInvoiceCommandHandler addProductsFromInvoiceCommandHandler;

    public StockApplicationServiceImpl(AddProductsFromInvoiceCommandHandler addProductsFromInvoiceCommandHandler) {
        this.addProductsFromInvoiceCommandHandler = addProductsFromInvoiceCommandHandler;
    }

    @Override
    public AddProductsFromInvoiceResponse addProductsFromInvoice(AddProductsFromInvoiceCommand addProductsFromInvoiceCommand) {
        return addProductsFromInvoiceCommandHandler.addProductsFromInvoice(addProductsFromInvoiceCommand);
    }
}
