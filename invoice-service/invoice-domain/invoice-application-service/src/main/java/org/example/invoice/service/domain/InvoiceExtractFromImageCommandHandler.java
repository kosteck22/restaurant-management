package org.example.invoice.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.invoice.service.domain.dto.extract.CompanyType;
import org.example.invoice.service.domain.entity.Invoice;
import org.example.invoice.service.domain.exception.InvoiceDomainException;
import org.example.invoice.service.domain.ports.output.service.InvoiceDataExtractor;
import org.example.invoice.service.domain.mapper.InvoiceDataMapper;
import org.example.invoice.service.domain.ports.output.repository.InvoiceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class InvoiceExtractFromImageCommandHandler {
    private final InvoiceDataMapper invoiceDataMapper;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDomainService invoiceDomainService;

    public InvoiceExtractFromImageCommandHandler(InvoiceDataMapper invoiceDataMapper, InvoiceRepository invoiceRepository, InvoiceDomainService invoiceDomainService) {
        this.invoiceDataMapper = invoiceDataMapper;
        this.invoiceRepository = invoiceRepository;
        this.invoiceDomainService = invoiceDomainService;
    }

    @Transactional
    public String extractInvoice(byte[] file, CompanyType company) {
        InvoiceDataExtractor extractor = company.getExtractor();

        Invoice invoice = extractor.extractInvoiceData(file);
        invoiceDomainService.validateAndInitiateInvoice(invoice);
        saveInvoice(invoice);
        return invoice.getId().toString();
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
