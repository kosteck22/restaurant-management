package org.example.warehouse.stock.service.dataaccess.stock.repository;

import org.example.warehouse.stock.service.dataaccess.stock.entity.StockEntity;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, UUID> {
    List<StockEntity> findByStatus(StockStatus status);

    @Query("SELECT s FROM StockEntity s WHERE s.status = :status")
    StockEntity findLatestStockByStatus(@Param("status") StockStatus status);
}
