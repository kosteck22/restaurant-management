package org.example.stock.take.service.application.rest;


import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.take.service.domain.dto.create.CreateStockTakeCommand;
import org.example.warehouse.stock.take.service.domain.dto.create.CreateStockTakeResponse;
import org.example.warehouse.stock.take.service.domain.ports.input.service.StockTakeApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/stock-takes", produces = "application/vnd.api.v1+json")
public class StockTakeController {
    private final StockTakeApplicationService stockTakeApplicationService;

    public StockTakeController(StockTakeApplicationService stockTakeApplicationService) {
        this.stockTakeApplicationService = stockTakeApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateStockTakeResponse> createStockTake(@RequestBody CreateStockTakeCommand createStockTakeCommand) {
        log.info("Creating stock take with date: {}", createStockTakeCommand.preparedDate());
        CreateStockTakeResponse createInvoiceResponse = stockTakeApplicationService.createStockTake(createStockTakeCommand);
        log.info("Stock take created with tracking id: {}", createInvoiceResponse.stockTakeTrackingId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createInvoiceResponse);
    }
}
