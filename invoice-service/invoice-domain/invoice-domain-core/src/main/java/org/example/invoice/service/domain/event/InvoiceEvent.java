package org.example.invoice.service.domain.event;

import org.example.domain.event.DomainEvent;
import org.example.invoice.service.domain.entity.Invoice;

import java.time.ZonedDateTime;

public abstract class InvoiceEvent implements DomainEvent<Invoice> {
    private final Invoice invoice;
    private final ZonedDateTime createdAt;

    public InvoiceEvent(Invoice invoice, ZonedDateTime createdAt) {
        this.invoice = invoice;
        this.createdAt = createdAt;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
