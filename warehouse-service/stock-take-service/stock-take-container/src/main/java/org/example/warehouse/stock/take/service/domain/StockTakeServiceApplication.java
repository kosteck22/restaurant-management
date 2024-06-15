package org.example.warehouse.stock.take.service.domain;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "org.example.stock.take.service.dataaccess"})
@EntityScan(basePackages = { "org.example.stock.take.service.dataaccess"})
@SpringBootApplication(scanBasePackages = "org.example")
public class StockTakeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockTakeServiceApplication.class, args);
    }

}
