package org.example.warehouse.stock.service.domain.ports.output.repository;

import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;

import java.util.List;

public interface StockRepository {
    List<Stock> findByStatus(StockStatus status);
    Stock save(Stock stock);
}
