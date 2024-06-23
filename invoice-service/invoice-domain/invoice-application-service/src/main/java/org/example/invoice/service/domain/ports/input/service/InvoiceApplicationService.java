package org.example.invoice.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.example.invoice.service.domain.dto.create.CreateInvoiceCommand;
import org.example.invoice.service.domain.dto.create.CreateInvoiceResponse;
import org.example.invoice.service.domain.dto.extract.CompanyType;

public interface InvoiceApplicationService {
    CreateInvoiceResponse createInvoice(@Valid CreateInvoiceCommand createInvoiceCommand);
    String extractInvoiceFromImage(byte[] file, CompanyType company);
}
