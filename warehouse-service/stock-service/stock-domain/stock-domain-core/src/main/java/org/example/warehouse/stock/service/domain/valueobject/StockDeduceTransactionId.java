package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class StockDeduceTransactionId extends BaseId<UUID> {
    public StockDeduceTransactionId(UUID value) {
        super(value);
    }
}
