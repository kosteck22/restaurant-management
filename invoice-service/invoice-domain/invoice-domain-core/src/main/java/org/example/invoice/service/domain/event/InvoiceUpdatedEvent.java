package org.example.invoice.service.domain.event;

import org.example.invoice.service.domain.entity.Invoice;

import java.time.ZonedDateTime;

public class InvoiceUpdatedEvent extends InvoiceEvent {
    public InvoiceUpdatedEvent(Invoice invoice, ZonedDateTime createdAt) {
        super(invoice, createdAt);
    }

    @Override
    public void fire() {
        //publish event
    }
}
