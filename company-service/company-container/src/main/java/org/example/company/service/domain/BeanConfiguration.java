package org.example.company.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public CompanyDomainService orderDomainService() {
        return new CompanyDomainServiceImpl();
    }
}
