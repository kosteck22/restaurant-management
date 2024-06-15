package org.example.warehouse.stock.take.service.domain.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "stock-take-service")
public class StockTakeServiceConfigData {
    private String stockTakeCreatedTopicName;
    private String stockTakeCreatedResponseTopicName;
}
