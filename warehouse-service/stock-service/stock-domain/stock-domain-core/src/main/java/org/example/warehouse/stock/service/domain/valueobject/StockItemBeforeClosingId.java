package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class StockItemBeforeClosingId extends BaseId<Long> {
    public StockItemBeforeClosingId(Long value) {
        super(value);
    }
}
