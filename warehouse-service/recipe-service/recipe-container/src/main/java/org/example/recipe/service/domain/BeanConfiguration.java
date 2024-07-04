package org.example.recipe.service.domain;


import org.example.warehouse.recipe.service.domain.RecipeDomainService;
import org.example.warehouse.recipe.service.domain.RecipeDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public RecipeDomainService productDomainService() {
        return new RecipeDomainServiceImpl();
    }
}
