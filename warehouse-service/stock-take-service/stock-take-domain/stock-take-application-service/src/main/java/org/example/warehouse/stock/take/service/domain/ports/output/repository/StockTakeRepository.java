package org.example.warehouse.stock.take.service.domain.ports.output.repository;

import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.warehouse.stock.take.service.domain.valueobject.StockTakeStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface StockTakeRepository {
    StockTake save(StockTake stockTake);
    List<StockTake> findByStatusAndPreparedDateAfter(StockTakeStatus status, LocalDateTime preparedDate);
}
