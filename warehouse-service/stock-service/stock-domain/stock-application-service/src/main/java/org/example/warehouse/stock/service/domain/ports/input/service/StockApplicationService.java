package org.example.warehouse.stock.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.example.warehouse.stock.service.domain.dto.addFromInvoice.AddProductsFromInvoiceCommand;
import org.example.warehouse.stock.service.domain.dto.addFromInvoice.AddProductsFromInvoiceResponse;

public interface StockApplicationService {
    AddProductsFromInvoiceResponse addProductsFromInvoice(@Valid AddProductsFromInvoiceCommand addProductsFromInvoiceCommand);
}
