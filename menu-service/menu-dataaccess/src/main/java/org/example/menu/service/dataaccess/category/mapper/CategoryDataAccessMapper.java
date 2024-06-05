package org.example.menu.service.dataaccess.category.mapper;

import org.example.menu.service.dataaccess.category.entity.CategoryEntity;
import org.example.menu.service.domain.entity.Category;
import org.example.menu.service.domain.valueobject.CategoryId;

public class CategoryDataAccessMapper {
    public Category categoryEntityToCategory(CategoryEntity categoryEntity) {
        return Category.builder()
                .categoryId(new CategoryId(categoryEntity.getId()))
                .name(categoryEntity.getName())
                .build();
    }
}
