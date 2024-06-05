package org.example.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class MenuItemId extends BaseId<UUID> {
    public MenuItemId(UUID value) {
        super(value);
    }
}
