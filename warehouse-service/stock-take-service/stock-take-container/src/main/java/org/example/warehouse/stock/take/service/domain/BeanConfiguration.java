package org.example.warehouse.stock.take.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public StockTakeDomainService stockTakeDomainService() {
        return new StockTakeDomainServiceImpl();
    }
}
