package org.example.dataaccess.recipe.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeItemEntityId implements Serializable {
    private Long id;
    private RecipeEntity recipe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeItemEntityId that = (RecipeItemEntityId) o;
        return Objects.equals(id, that.id) && Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipe);
    }
}
