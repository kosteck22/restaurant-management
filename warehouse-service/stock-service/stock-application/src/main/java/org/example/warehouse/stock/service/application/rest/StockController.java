package org.example.warehouse.stock.service.application.rest;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceCommand;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceResponse;
import org.example.warehouse.stock.service.domain.ports.input.service.StockApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/stocks", produces = "application/vnd.api.v1+json")
public class StockController {
    private final StockApplicationService stockApplicationService;

    public StockController(StockApplicationService stockApplicationService) {
        this.stockApplicationService = stockApplicationService;
    }

    @PostMapping
    public ResponseEntity<AddProductsFromInvoiceResponse> addProductsFromInvoice(@RequestBody AddProductsFromInvoiceCommand addProductsFromInvoiceCommand) {
        log.info("Adding products to stock for invoice id: {}", addProductsFromInvoiceCommand.invoiceId());
        AddProductsFromInvoiceResponse addProductsFromInvoiceResponse = stockApplicationService.addProductsFromInvoice(addProductsFromInvoiceCommand);
        log.info("Products added to stock for invoice id: {}", addProductsFromInvoiceCommand.invoiceId());
        return ResponseEntity.status(HttpStatus.OK).body(addProductsFromInvoiceResponse);
    }

    @GetMapping("/cogs")
    public ResponseEntity<BigDecimal> getCostOfGoodsSold() {
        log.info("Getting cost of goods sold");
        BigDecimal cogs = stockApplicationService.getCostOfGoodsSold();
        return ResponseEntity.ok(cogs);
    }

    @GetMapping("/variance")
    public ResponseEntity<Map<UUID, BigDecimal>> getVariance() {
        log.info("Getting variance");
        Map<UUID, BigDecimal> result = stockApplicationService.getVariance();
        return ResponseEntity.ok(result);
    }
}
