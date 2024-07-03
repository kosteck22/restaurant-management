package org.example.warehouse.recipe.service.domain.ports.output.repositorty;

import org.example.warehouse.recipe.service.domain.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository  {
    List<Product> findProductsById(List<UUID> productIds);
}
