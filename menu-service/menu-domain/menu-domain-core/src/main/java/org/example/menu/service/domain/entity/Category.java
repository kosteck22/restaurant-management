package org.example.menu.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.menu.service.domain.valueobject.CategoryId;

public class Category extends BaseEntity<CategoryId> {
    private final String name;

    private Category(Builder builder) {
        setId(builder.categoryId);
        name = builder.name;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CategoryId categoryId;
        private String name;

        private Builder() {
        }

        public Builder categoryId(CategoryId val) {
            categoryId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
