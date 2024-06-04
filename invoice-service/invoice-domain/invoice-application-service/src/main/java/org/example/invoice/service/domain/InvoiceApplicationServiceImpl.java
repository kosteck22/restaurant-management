package org.example.invoice.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.example.invoice.service.domain.dto.create.CreateInvoiceCommand;
import org.example.invoice.service.domain.dto.create.CreateInvoiceResponse;
import org.example.invoice.service.domain.ports.input.service.InvoiceApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class InvoiceApplicationServiceImpl implements InvoiceApplicationService {
    private final InvoiceCreateCommandHandler invoiceCreateCommandHandler;

    public InvoiceApplicationServiceImpl(InvoiceCreateCommandHandler invoiceCreateCommandHandler) {
        this.invoiceCreateCommandHandler = invoiceCreateCommandHandler;
    }

    @Override
    public CreateInvoiceResponse createInvoice(CreateInvoiceCommand createInvoiceCommand) {
        return invoiceCreateCommandHandler.createInvoice(createInvoiceCommand);
    }
}
