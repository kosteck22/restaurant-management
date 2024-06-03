package org.example.invoice.service.domain;

import org.example.invoice.service.domain.entity.Invoice;
import org.example.invoice.service.domain.event.InvoiceCreatedEvent;
import org.example.invoice.service.domain.event.InvoiceDeletedEvent;
import org.example.invoice.service.domain.event.InvoiceUpdatedEvent;

public interface InvoiceDomainService {
    InvoiceCreatedEvent validateAndInitiateInvoice(Invoice invoice);
}
