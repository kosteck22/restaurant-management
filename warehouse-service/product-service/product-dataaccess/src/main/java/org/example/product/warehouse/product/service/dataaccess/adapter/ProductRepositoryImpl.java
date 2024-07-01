package org.example.product.warehouse.product.service.dataaccess.adapter;

import org.example.product.warehouse.product.service.dataaccess.mapper.ProductDataAccessMapper;
import org.example.dataaccess.product.repository.ProductJpaRepository;
import org.example.warehouse.product.service.domain.entity.Product;
import org.example.warehouse.product.service.domain.ports.output.repository.ProductRepository;
import org.example.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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
    public Product save(Product product) {
        return productDataAccessMapper.productEntityToProduct(
                productJpaRepository.save(productDataAccessMapper.productToProductEntity(product)));
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return productJpaRepository.findById(productId.getValue())
                .map(productDataAccessMapper::productEntityToProduct);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(productDataAccessMapper::productEntityToProduct)
                .collect(Collectors.toList());
    }
}
