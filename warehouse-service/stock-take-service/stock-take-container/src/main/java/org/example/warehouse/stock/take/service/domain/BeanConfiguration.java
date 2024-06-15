package org.example.warehouse.stock.take.service.domain;

import org.example.warehouse.stock.take.service.domain.StockTakeDomainService;
import org.example.warehouse.stock.take.service.domain.StockTakeDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public StockTakeDomainService saleDomainService() {
        return new StockTakeDomainServiceImpl();
    }
}
