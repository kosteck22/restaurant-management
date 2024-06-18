package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class StockItemBeforeClosingId extends BaseId<UUID> {
    public StockItemBeforeClosingId(UUID value) {
        super(value);
    }
}
