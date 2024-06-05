package org.example.menu.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public MenuDomainService menuDomainService() {
        return new MenuDomainServiceImpl();
    }
}
