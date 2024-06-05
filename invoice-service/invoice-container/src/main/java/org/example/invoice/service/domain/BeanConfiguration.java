package org.example.invoice.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public InvoiceDomainService invoiceDomainService() {
        return new InvoiceDomainServiceImpl();
    }
}
