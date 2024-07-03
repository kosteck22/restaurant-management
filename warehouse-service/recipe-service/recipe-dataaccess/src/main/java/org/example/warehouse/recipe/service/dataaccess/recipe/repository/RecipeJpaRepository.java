package org.example.warehouse.recipe.service.dataaccess.recipe.repository;

import org.example.warehouse.recipe.service.dataaccess.recipe.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeJpaRepository extends JpaRepository<RecipeEntity, UUID> {
}
