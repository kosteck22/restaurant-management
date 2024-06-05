package org.example.sale.service.domain.ports.output.repository;

import org.example.sale.service.domain.entity.Sale;
import org.example.sale.service.domain.valueobject.SaleId;

import java.util.Optional;

public interface SaleRepository {
    Sale save(Sale sale);
    Optional<Sale> findById(SaleId saleId);
}
