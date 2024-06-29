package org.example.warehouse.stock.service.application.rest;


import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceCommand;
import org.example.warehouse.stock.service.domain.dto.service.add.AddProductsFromInvoiceResponse;
import org.example.warehouse.stock.service.domain.ports.input.service.StockApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
