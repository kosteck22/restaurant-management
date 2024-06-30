package org.example.warehouse.stock.take.service.dataaccess.product.adapter;

import org.example.dataaccess.product.entity.ProductEntity;
import org.example.dataaccess.product.repository.ProductJpaRepository;
import org.example.warehouse.stock.take.service.dataaccess.product.mapper.ProductDataAccessMapper;
import org.example.warehouse.stock.take.service.domain.entity.Product;
import org.example.warehouse.stock.take.service.domain.ports.output.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;
    private final ProductDataAccessMapper productDataAccessMapper;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository, ProductDataAccessMapper productDataAccessMapper) {
        this.productJpaRepository = productJpaRepository;
        this.productDataAccessMapper = productDataAccessMapper;
    }

    @Override
    public List<Product> findProductsInformation(List<Product> products) {
        List<UUID> productIds = products.stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());

        return productJpaRepository.findAllById(productIds).stream()
                .map(productDataAccessMapper::productEntityToProduct)
                .collect(Collectors.toList());
    }
}
