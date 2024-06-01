package org.example.invoice.service.domain.valueobject;

import org.example.domain.valueobject.BaseId;

import java.util.UUID;

public class CompanyId extends BaseId<UUID> {
    public CompanyId(UUID value) {
        super(value);
    }
}
