package org.example.invoice.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.example.invoice.service.domain.dto.create.CreateInvoiceCommand;
import org.example.invoice.service.domain.dto.create.CreateInvoiceResponse;

public interface InvoiceApplicationService {
    CreateInvoiceResponse createInvoice(@Valid CreateInvoiceCommand createInvoiceCommand);
}
