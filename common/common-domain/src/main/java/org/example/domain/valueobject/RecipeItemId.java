package org.example.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class RecipeItemId extends BaseId<Long> {
    public RecipeItemId(Long value) {
        super(value);
    }
}
