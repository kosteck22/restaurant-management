package org.example.sale.service.dataaccess.sale.repository;

import org.example.sale.service.dataaccess.sale.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaleJpaRepository extends JpaRepository<SaleEntity, UUID> {
}
