package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class StockAddTransactionId extends BaseId<Long> {
    public StockAddTransactionId(Long value) {
        super(value);
    }
}
