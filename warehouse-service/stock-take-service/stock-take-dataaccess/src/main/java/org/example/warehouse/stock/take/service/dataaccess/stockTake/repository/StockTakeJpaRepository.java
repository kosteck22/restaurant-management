package org.example.warehouse.stock.take.service.dataaccess.stockTake.repository;

import org.example.warehouse.stock.take.service.dataaccess.stockTake.entity.StockTakeEntity;
import org.example.warehouse.stock.take.service.domain.valueobject.StockTakeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface StockTakeJpaRepository extends JpaRepository<StockTakeEntity, UUID> {
    List<StockTakeEntity> findByStatusAndPreparedDateAfter(StockTakeStatus status, LocalDateTime preparedDate);
}
