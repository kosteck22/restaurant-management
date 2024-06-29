package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class RecipeItemId extends BaseId<UUID> {
    public RecipeItemId(UUID value) {
        super(value);
    }
}
