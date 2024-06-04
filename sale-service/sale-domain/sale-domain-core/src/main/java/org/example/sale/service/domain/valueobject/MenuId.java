package org.example.sale.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class MenuId extends BaseId<UUID> {
    public MenuId(UUID value) {
        super(value);
    }
}
