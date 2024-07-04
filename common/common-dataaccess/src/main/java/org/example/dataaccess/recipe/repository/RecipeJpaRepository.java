package org.example.dataaccess.recipe.repository;

import org.example.dataaccess.recipe.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeJpaRepository extends JpaRepository<RecipeEntity, UUID> {
    @Query("SELECT r FROM RecipeEntity r WHERE r.menuItemId IN :menuItemIds AND r.createdAt = (SELECT MAX(r2.createdAt) FROM RecipeEntity r2 WHERE r2.menuItemId = r.menuItemId)")
    List<RecipeEntity> findNewestRecipesByMenuItemIds(@Param("menuItemIds") List<UUID> menuItemIds);
}
