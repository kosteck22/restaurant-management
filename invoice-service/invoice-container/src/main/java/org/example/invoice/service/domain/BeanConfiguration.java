package org.example.invoice.service.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;

@Configuration
public class BeanConfiguration {
    @Bean
    public TextractClient textractClient() {
        return TextractClient.builder()
                .region(Region.EU_CENTRAL_1)
                .build();
    }

    @Bean
    public InvoiceDomainService invoiceDomainService() {
        return new InvoiceDomainServiceImpl();
    }
}
