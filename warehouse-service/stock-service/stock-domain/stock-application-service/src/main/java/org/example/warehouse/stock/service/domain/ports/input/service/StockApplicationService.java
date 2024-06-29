package org.example.warehouse.stock.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceCommand;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceResponse;

public interface StockApplicationService {
    AddProductsFromInvoiceResponse addProductsFromInvoice(@Valid AddProductsFromInvoiceCommand addProductsFromInvoiceCommand);
}
