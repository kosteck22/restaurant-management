package org.example.company.service.domain;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"org.example.company.service.dataaccess"})
@EntityScan(basePackages = {"org.example.company.service.dataaccess"})
@SpringBootApplication(scanBasePackages = {"org.example"})
@ComponentScan(basePackages = {"org.example"})
@EnableFeignClients
public class CompanyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
    }
}
