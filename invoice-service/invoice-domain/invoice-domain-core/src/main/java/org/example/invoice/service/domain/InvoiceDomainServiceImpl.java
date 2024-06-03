package org.example.invoice.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.invoice.service.domain.entity.Invoice;
import org.example.invoice.service.domain.event.InvoiceCreatedEvent;
import org.example.invoice.service.domain.event.InvoiceDeletedEvent;
import org.example.invoice.service.domain.event.InvoiceUpdatedEvent;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.example.domain.DomainConstants.UTC;

@Slf4j
public class InvoiceDomainServiceImpl implements InvoiceDomainService {
    @Override
    public InvoiceCreatedEvent validateAndInitiateInvoice(Invoice invoice) {
        invoice.validateInvoice();
        invoice.initializeInvoice();
        log.info("Invoice with id: {} is initiated", invoice.getId().getValue());
        return new InvoiceCreatedEvent(invoice, ZonedDateTime.now(ZoneId.of(UTC)));
    }

}
