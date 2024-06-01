package org.example.invoice.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class ProductId extends BaseId<UUID> {
    public ProductId(UUID value) {
        super(value);
    }
}
