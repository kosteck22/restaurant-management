package org.example.warehouse.product.service.domain.ports.input.service;

import org.example.warehouse.product.service.domain.dto.get.ProductResponse;

import java.util.List;

public interface ProductApplicationService {
    List<ProductResponse> getProducts();
}
