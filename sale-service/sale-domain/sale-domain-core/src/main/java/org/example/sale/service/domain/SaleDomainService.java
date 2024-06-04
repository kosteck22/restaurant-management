package org.example.sale.service.domain;

import org.example.sale.service.domain.entity.Menu;
import org.example.sale.service.domain.entity.Sale;
import org.example.sale.service.domain.event.SaleCreatedEvent;
import org.example.sale.service.domain.event.SalePaidEvent;

import java.util.List;

public interface SaleDomainService {
    SaleCreatedEvent validateAndInitiateSale(Sale sale, Menu menu);
    SalePaidEvent paySale(Sale sale);
    void cancelSale(Sale sale, List<String> failureMessages);
}
