package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

public class InvoiceItemId extends BaseId<Long> {
    public InvoiceItemId(Long value) {
        super(value);
    }
}
