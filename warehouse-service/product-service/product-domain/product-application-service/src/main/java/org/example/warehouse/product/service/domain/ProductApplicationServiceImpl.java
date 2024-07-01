package org.example.warehouse.product.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.product.service.domain.dto.get.ProductResponse;
import org.example.warehouse.product.service.domain.mapper.ProductDataMapper;
import org.example.warehouse.product.service.domain.ports.input.service.ProductApplicationService;
import org.example.warehouse.product.service.domain.ports.output.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductApplicationServiceImpl implements ProductApplicationService {
    private final ProductRepository productRepository;
    private final ProductDataMapper productDataMapper;

    public ProductApplicationServiceImpl(ProductRepository productRepository, ProductDataMapper productDataMapper) {
        this.productRepository = productRepository;
        this.productDataMapper = productDataMapper;
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(productDataMapper::productToProductResponse)
                .collect(Collectors.toList());
    }
}
