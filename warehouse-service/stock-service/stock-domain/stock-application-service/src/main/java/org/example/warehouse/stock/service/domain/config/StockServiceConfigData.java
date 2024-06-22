package org.example.warehouse.stock.service.domain.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "stock-service")
public class StockServiceConfigData {
    private String stockUpdateRequestTopicName;
    private String stockUpdateResponseTopicName;
}
