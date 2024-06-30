package org.example.warehouse.stock.service.dataaccess.stock.repository;

import org.example.warehouse.stock.service.dataaccess.stock.entity.StockEntity;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockJpaRepository extends JpaRepository<StockEntity, UUID> {
    List<StockEntity> findByStatus(StockStatus status);
}
