package org.example.dataaccess.recipe.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RecipeItemEntityId.class)
@Table(name = "recipe-items")
@Entity
public class RecipeItemEntity {
    @Id
    private Long id;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RECIPE_ID")
    private RecipeEntity recipe;

    private UUID productId;
    private BigDecimal quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeItemEntity that = (RecipeItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipe);
    }
}
