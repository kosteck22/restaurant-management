package org.example.sale.service.application.rest;


import lombok.extern.slf4j.Slf4j;
import org.example.sale.service.domain.dto.complete.CompleteSaleResponse;
import org.example.sale.service.domain.dto.create.CreateSaleCommand;
import org.example.sale.service.domain.dto.create.CreateSaleResponse;
import org.example.sale.service.domain.ports.input.service.SaleApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/sales", produces = "application/vnd.api.v1+json")
public class SaleController {
    private final SaleApplicationService saleApplicationService;

    public SaleController(SaleApplicationService saleApplicationService) {
        this.saleApplicationService = saleApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateSaleResponse> createSale(@RequestBody CreateSaleCommand createMenuItemCommand) {
        log.info("Creating sale");
        CreateSaleResponse createSaleResponse = saleApplicationService.createSale(createMenuItemCommand);
        log.info("Sale created with id: {}", createSaleResponse.saleId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createSaleResponse);
    }

    @PostMapping("/complete/{saleId}")
    public ResponseEntity<CompleteSaleResponse> completeSale(@PathVariable(name = "saleId") UUID saleId) {
        log.info("Trying to complete sale with id: {}", saleId);
        CompleteSaleResponse completeSaleResponse = saleApplicationService.completeSale(saleId);
        log.info("Sale completed with id: {}", completeSaleResponse.saleId());
        return ResponseEntity.status(HttpStatus.OK).body(completeSaleResponse);
    }
}
