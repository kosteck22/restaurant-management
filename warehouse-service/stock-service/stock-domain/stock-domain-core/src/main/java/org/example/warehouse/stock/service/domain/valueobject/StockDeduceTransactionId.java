package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class StockDeduceTransactionId extends BaseId<Long> {
    public StockDeduceTransactionId(Long value) {
        super(value);
    }
}
