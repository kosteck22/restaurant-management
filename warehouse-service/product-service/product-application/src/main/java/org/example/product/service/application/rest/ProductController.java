package org.example.product.service.application.rest;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.product.service.domain.dto.get.ProductResponse;
import org.example.warehouse.product.service.domain.ports.input.service.ProductApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/products", produces = "application/vnd.api.v1+json")
public class ProductController {

    private final ProductApplicationService productApplicationService;

    public ProductController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        log.info("Getting list of products");
        List<ProductResponse> products = productApplicationService.getProducts();
        log.info("Product list with size: {}", products.size());

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(List.of());
        }
        return ResponseEntity.ok(products);
    }
}
