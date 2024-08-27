package org.example.warehouse.stock.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceCommand;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceResponse;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public interface StockApplicationService {
    AddProductsFromInvoiceResponse addProductsFromInvoice(@Valid AddProductsFromInvoiceCommand addProductsFromInvoiceCommand);

    BigDecimal getCostOfGoodsSold();

    Map<UUID, BigDecimal> getVariance();
}
