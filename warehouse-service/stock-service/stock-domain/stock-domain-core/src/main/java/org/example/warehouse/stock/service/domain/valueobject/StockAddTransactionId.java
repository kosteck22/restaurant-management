package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class StockAddTransactionId extends BaseId<UUID> {
    public StockAddTransactionId(UUID value) {
        super(value);
    }
}
