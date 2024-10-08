package org.example.sale.service.domain.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sale-service")
public class SaleServiceConfigData {
    private String stockSubtractRequestTopicName;
    private String stockSubtractResponseTopicName;
}
