package org.example.warehouse.stock.service.domain;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public StockDomainService stockDomainService() {
        return new StockDomainServiceImpl();
    }
}
