package org.example.invoice.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderId extends BaseId<UUID> {
    public OrderId(UUID value) {
        super(value);
    }
}
