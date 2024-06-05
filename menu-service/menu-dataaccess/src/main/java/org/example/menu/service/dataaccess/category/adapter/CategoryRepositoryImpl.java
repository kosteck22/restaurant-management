package org.example.menu.service.dataaccess.category.adapter;

import org.example.menu.service.dataaccess.category.mapper.CategoryDataAccessMapper;
import org.example.menu.service.dataaccess.category.repository.CategoryJpaRepository;
import org.example.menu.service.domain.entity.Category;
import org.example.menu.service.domain.ports.output.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryDataAccessMapper categoryDataAccessMapper;

    public CategoryRepositoryImpl(CategoryJpaRepository categoryJpaRepository,
                                  CategoryDataAccessMapper categoryDataAccessMapper) {
        this.categoryJpaRepository = categoryJpaRepository;
        this.categoryDataAccessMapper = categoryDataAccessMapper;
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryJpaRepository.findByName(name).map(categoryDataAccessMapper::categoryEntityToCategory);
    }
}
