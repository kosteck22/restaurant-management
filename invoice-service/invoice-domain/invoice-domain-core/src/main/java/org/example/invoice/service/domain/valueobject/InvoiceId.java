package org.example.invoice.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class InvoiceId extends BaseId<UUID> {
    public InvoiceId(UUID value) {
        super(value);
    }
}
