package org.example.product.service.domain;


import org.example.warehouse.product.service.domain.ProductDomainService;
import org.example.warehouse.product.service.domain.ProductDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public ProductDomainService productDomainService() {
        return new ProductDomainServiceImpl();
    }
}
