package org.example.warehouse.stock.service.domain.ports.output.repository;

import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository {
    List<Stock> findByStatus(StockStatus status);
    Stock save(Stock stock);
    Optional<Stock> findLatestStockByStatus(@Param("status") StockStatus stockStatus);
}
