package org.example.sale.service.domain.event;

import org.example.sale.service.domain.entity.Sale;

import java.time.ZonedDateTime;

public class SalePaidEvent extends SaleEvent {
    public SalePaidEvent(Sale sale, ZonedDateTime createdAt) {
        super(sale, createdAt);
    }

    @Override
    public void fire() {
        //publish event
    }
}
