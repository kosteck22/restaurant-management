package org.example.warehouse.stock.take.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class StockItemId extends BaseId<Long> {
    public StockItemId(Long value) {
        super(value);
    }
}
