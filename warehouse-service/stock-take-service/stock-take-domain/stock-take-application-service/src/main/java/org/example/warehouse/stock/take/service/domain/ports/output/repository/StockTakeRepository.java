package org.example.warehouse.stock.take.service.domain.ports.output.repository;

import org.example.domain.valueobject.StockTakeId;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.warehouse.stock.take.service.domain.valueobject.StockTakeStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StockTakeRepository {
    StockTake save(StockTake stockTake);
    List<StockTake> findByStatusAndPreparedDateAfter(StockTakeStatus status, LocalDate preparedDate);

    Optional<StockTake> findById(StockTakeId stockTakeId);
}
