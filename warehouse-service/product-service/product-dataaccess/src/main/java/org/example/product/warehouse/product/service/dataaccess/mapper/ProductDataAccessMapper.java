package org.example.product.warehouse.product.service.dataaccess.mapper;

import org.example.domain.valueobject.UnitOfMeasure;
import org.example.product.warehouse.product.service.dataaccess.entity.ProductEntity;
import org.example.warehouse.product.service.domain.entity.Product;
import org.example.domain.valueobject.Category;
import org.example.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

@Component
public class ProductDataAccessMapper {
    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.builder()
                .id(new ProductId(productEntity.getId()))
                .name(productEntity.getName())
                .category(new Category(productEntity.getCategory()))
                .unitOfMeasure(new UnitOfMeasure(productEntity.getUnitOfMeasure()))
                .build();
    }

    public ProductEntity productToProductEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId().getValue())
                .name(product.getName())
                .category(product.getCategory().getName())
                .unitOfMeasure(product.getUnitOfMeasure().getName())
                .build();
    }
}
