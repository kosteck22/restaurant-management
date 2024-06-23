package org.example.invoice.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.example.invoice.service.domain.dto.create.CreateInvoiceCommand;
import org.example.invoice.service.domain.dto.create.CreateInvoiceResponse;
import org.example.invoice.service.domain.dto.extract.CompanyType;
import org.example.invoice.service.domain.ports.input.service.InvoiceApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class InvoiceApplicationServiceImpl implements InvoiceApplicationService {
    private final InvoiceCreateCommandHandler invoiceCreateCommandHandler;
    private final InvoiceExtractFromImageCommandHandler invoiceExtractFromImageCommandHandler;

    public InvoiceApplicationServiceImpl(InvoiceCreateCommandHandler invoiceCreateCommandHandler, InvoiceExtractFromImageCommandHandler invoiceExtractCommandHelper) {
        this.invoiceCreateCommandHandler = invoiceCreateCommandHandler;
        this.invoiceExtractFromImageCommandHandler = invoiceExtractCommandHelper;
    }

    @Override
    public CreateInvoiceResponse createInvoice(CreateInvoiceCommand createInvoiceCommand) {
        return invoiceCreateCommandHandler.createInvoice(createInvoiceCommand);
    }

    @Override
    public String extractInvoiceFromImage(byte[] file, CompanyType company) {
        return invoiceExtractFromImageCommandHandler.extractInvoice(file, company);
    }
}
