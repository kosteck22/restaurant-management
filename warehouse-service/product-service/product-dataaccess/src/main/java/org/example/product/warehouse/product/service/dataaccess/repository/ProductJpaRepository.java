package org.example.product.warehouse.product.service.dataaccess.repository;

import org.example.product.warehouse.product.service.dataaccess.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
}
