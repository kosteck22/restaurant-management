package org.example.warehouse.recipe.service.dataaccess.product.adapter;

import org.example.dataaccess.product.repository.ProductJpaRepository;
import org.example.warehouse.recipe.service.dataaccess.product.mapper.ProductDataAccessMapper;
import org.example.warehouse.recipe.service.domain.entity.Product;
import org.example.warehouse.recipe.service.domain.ports.output.repositorty.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;
    private final ProductDataAccessMapper productDataAccessMapper;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository,
                                 ProductDataAccessMapper productDataAccessMapper) {
        this.productJpaRepository = productJpaRepository;
        this.productDataAccessMapper = productDataAccessMapper;
    }

    @Override
    public List<Product> findProductsById(List<UUID> productIds) {
        return productJpaRepository.findByIdIn(productIds).stream()
                .map(productDataAccessMapper::productEntityToProduct)
                .collect(Collectors.toList());
    }
}
