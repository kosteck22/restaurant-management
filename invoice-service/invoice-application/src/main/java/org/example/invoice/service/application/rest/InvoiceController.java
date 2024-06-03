package org.example.invoice.service.application.rest;


import lombok.extern.slf4j.Slf4j;
import org.example.invoice.service.domain.dto.create.CreateInvoiceCommand;
import org.example.invoice.service.domain.dto.create.CreateInvoiceResponse;
import org.example.invoice.service.domain.ports.input.service.InvoiceApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/invoices", produces = "application/vnd.api.v1+json")
public class InvoiceController {
    private final InvoiceApplicationService invoiceApplicationService;

    public InvoiceController(InvoiceApplicationService invoiceApplicationService) {
        this.invoiceApplicationService = invoiceApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateInvoiceResponse> createInvoice(@RequestBody CreateInvoiceCommand createInvoiceCommand) {
        log.info("Creating invoice with number: {}", createInvoiceCommand.number());
        CreateInvoiceResponse createInvoiceResponse = invoiceApplicationService.createInvoice(createInvoiceCommand);
        log.info("Invoice created with invoiceId: {}", createInvoiceResponse.invoiceId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createInvoiceResponse);
    }
}
