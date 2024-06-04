package org.example.sale.service.domain.event;

import org.example.domain.event.DomainEvent;
import org.example.sale.service.domain.entity.Sale;

import java.time.ZonedDateTime;

public abstract class SaleEvent implements DomainEvent<Sale> {
    private final Sale sale;
    private final ZonedDateTime createdAt;

    public SaleEvent(Sale sale, ZonedDateTime createdAt) {
        this.sale = sale;
        this.createdAt = createdAt;
    }

    public Sale getSale() {
        return sale;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
