package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class StockId extends BaseId<UUID> {
    public StockId(UUID value) {
        super(value);
    }
}
