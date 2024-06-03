package org.example.invoice.service.domain.event;

import org.example.invoice.service.domain.entity.Invoice;

import java.time.ZonedDateTime;

public class InvoiceDeletedEvent extends InvoiceEvent {
    public InvoiceDeletedEvent(Invoice invoice, ZonedDateTime createdAt) {
        super(invoice, createdAt);
    }

    @Override
    public void fire() {
        //publish event
    }
}
