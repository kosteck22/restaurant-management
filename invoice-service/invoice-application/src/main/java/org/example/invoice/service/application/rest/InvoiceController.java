package org.example.invoice.service.application.rest;


import lombok.extern.slf4j.Slf4j;
import org.example.invoice.service.domain.dto.create.CreateInvoiceCommand;
import org.example.invoice.service.domain.dto.create.CreateInvoiceResponse;
import org.example.invoice.service.domain.dto.extract.CompanyType;
import org.example.invoice.service.domain.ports.input.service.InvoiceApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping(value = "/extract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> extractInvoiceFromImage(
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "company") CompanyType company) throws IOException {
        log.info("Extracting invoice from file for company: {}", company.name());
        String id = invoiceApplicationService.extractInvoiceFromImage(file.getBytes(), company);
        return ResponseEntity.ok(id);
    }
}
