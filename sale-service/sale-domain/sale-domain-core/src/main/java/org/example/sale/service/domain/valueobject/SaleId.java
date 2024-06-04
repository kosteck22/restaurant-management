package org.example.sale.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class SaleId extends BaseId<UUID> {
    public SaleId(UUID value) {
        super(value);
    }
}
