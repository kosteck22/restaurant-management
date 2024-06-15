package org.example.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class StockTakeId extends BaseId<UUID> {
    public StockTakeId(UUID value) {
        super(value);
    }
}
