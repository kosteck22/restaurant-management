package org.example.invoice.service.domain.event;

import org.example.invoice.service.domain.entity.Invoice;

import java.time.ZonedDateTime;

public class InvoiceCreatedEvent extends InvoiceEvent {
    public InvoiceCreatedEvent(Invoice invoice, ZonedDateTime createdAt) {
        super(invoice, createdAt);
    }

    @Override
    public void fire() {
        //publish event
    }
}
