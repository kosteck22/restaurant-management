package org.example.menu.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class CategoryId extends BaseId<UUID> {
    public CategoryId(UUID value) {
        super(value);
    }
}
