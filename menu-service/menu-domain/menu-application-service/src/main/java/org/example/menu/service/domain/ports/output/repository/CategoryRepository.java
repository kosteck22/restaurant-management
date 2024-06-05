package org.example.menu.service.domain.ports.output.repository;

import org.example.menu.service.domain.entity.Category;
import org.example.menu.service.domain.valueobject.CategoryId;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    Optional<Category> findByName(String name);
}
