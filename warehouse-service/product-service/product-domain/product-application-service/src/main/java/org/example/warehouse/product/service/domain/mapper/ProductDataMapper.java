package org.example.warehouse.product.service.domain.mapper;

import org.example.warehouse.product.service.domain.dto.get.ProductResponse;
import org.example.warehouse.product.service.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDataMapper {
    public ProductResponse productToProductResponse(Product product) {
        return new ProductResponse(
                product.getId().getValue(),
                product.getName(),
                product.getCategory().getName()
        );
    }
}
