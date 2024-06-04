package org.example.invoice.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.example.invoice.service.domain.dto.create.CreateInvoiceCommand;
import org.example.invoice.service.domain.dto.create.CreateInvoiceResponse;
import org.example.invoice.service.domain.entity.Invoice;
import org.example.invoice.service.domain.event.InvoiceCreatedEvent;
import org.example.invoice.service.domain.exception.InvoiceDomainException;
import org.example.invoice.service.domain.mapper.InvoiceDataMapper;
import org.example.invoice.service.domain.ports.output.repository.InvoiceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class InvoiceCreateCommandHandler {

    private final InvoiceDataMapper invoiceDataMapper;
    private final InvoiceDomainService invoiceDomainService;
    private final InvoiceRepository invoiceRepository;

    public InvoiceCreateCommandHandler(InvoiceDataMapper invoiceDataMapper,
                                       InvoiceDomainService invoiceDomainService,
                                       InvoiceRepository invoiceRepository) {
        this.invoiceDataMapper = invoiceDataMapper;
        this.invoiceDomainService = invoiceDomainService;
        this.invoiceRepository = invoiceRepository;
    }


    @Transactional
    public CreateInvoiceResponse createInvoice(CreateInvoiceCommand createInvoiceCommand) {
        Invoice invoice = invoiceDataMapper.createInvoiceCommandToInvoice(createInvoiceCommand);
        InvoiceCreatedEvent invoiceCreatedEvent = invoiceDomainService.validateAndInitiateInvoice(invoice);
        saveInvoice(invoice);
        log.info("Invoice is created with id: {}", invoiceCreatedEvent.getInvoice().getId().getValue());
        return invoiceDataMapper.invoiceToCreateInvoiceResponse(invoiceCreatedEvent.getInvoice(),
                "Invoice created successfully");
    }

    private Invoice saveInvoice(Invoice invoice) {
        Invoice invoiceResult = invoiceRepository.save(invoice);
        if (invoiceResult == null) {
            log.error("Could not save invoice!");
            throw new InvoiceDomainException("Could not save invoice!");
        }

        log.info("Invoice is saved with id: {}", invoiceResult.getId().getValue());
        return invoiceResult;
    }
}
