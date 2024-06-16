package org.example.warehouse.stock.service.dataaccess.product.mapper;

import org.example.dataaccess.product.entity.ProductEntity;
import org.example.domain.valueobject.Category;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.UnitOfMeasure;
import org.example.warehouse.stock.service.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDataAccessMapper {

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.builder()
                .productId(new ProductId(productEntity.getId()))
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
