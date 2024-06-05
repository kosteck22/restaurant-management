package org.example.sale.service.domain;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "org.example.sale.service.dataaccess"})
@EntityScan(basePackages = { "org.example.sale.service.dataaccess"})
@SpringBootApplication(scanBasePackages = "org.example")
public class SaleServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaleServiceApplication.class, args);
    }
}
