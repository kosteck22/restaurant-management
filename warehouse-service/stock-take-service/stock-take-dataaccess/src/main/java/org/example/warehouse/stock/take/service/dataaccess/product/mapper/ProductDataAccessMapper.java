package org.example.warehouse.stock.take.service.dataaccess.product.mapper;

import org.example.dataaccess.product.entity.ProductEntity;
import org.example.domain.valueobject.Category;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.UnitOfMeasure;
import org.example.warehouse.stock.take.service.domain.entity.Product;
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
}
