package org.example.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class RecipeId extends BaseId<UUID> {
    public RecipeId(UUID value) {
        super(value);
    }
}
