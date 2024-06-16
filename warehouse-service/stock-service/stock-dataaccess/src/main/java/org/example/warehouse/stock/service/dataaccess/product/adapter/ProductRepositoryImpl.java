package org.example.warehouse.stock.service.dataaccess.product.adapter;

import org.example.dataaccess.product.repository.ProductJpaRepository;
import org.example.domain.valueobject.Category;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.UnitOfMeasure;
import org.example.warehouse.stock.service.dataaccess.product.mapper.ProductDataAccessMapper;
import org.example.warehouse.stock.service.domain.entity.Product;
import org.example.warehouse.stock.service.domain.ports.output.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(productDataAccessMapper::productEntityToProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return productJpaRepository.saveAll(products.stream()
                        .map(productDataAccessMapper::productToProductEntity)
                        .collect(Collectors.toList())).stream()
                .map(productDataAccessMapper::productEntityToProduct)
                .collect(Collectors.toList());
    }
}
