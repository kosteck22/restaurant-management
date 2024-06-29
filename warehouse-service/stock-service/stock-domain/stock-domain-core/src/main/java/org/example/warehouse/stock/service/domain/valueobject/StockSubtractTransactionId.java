package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

public class StockSubtractTransactionId extends BaseId<Long> {
    public StockSubtractTransactionId(Long value) {
        super(value);
    }
}
