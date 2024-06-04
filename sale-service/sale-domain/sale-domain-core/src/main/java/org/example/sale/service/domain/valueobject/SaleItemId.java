package org.example.sale.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class SaleItemId extends BaseId<Long> {
    public SaleItemId(Long value) {
        super(value);
    }
}
