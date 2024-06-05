package org.example.sale.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public SaleDomainService saleDomainService() {
        return new SaleDomainServiceImpl();
    }
}
