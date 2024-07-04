package org.example.recipe.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "org.example.warehouse.recipe.service.dataaccess", "org.example.dataaccess"})
@EntityScan(basePackages = { "org.example.warehouse.recipe.service.dataaccess", "org.example.dataaccess"})
@SpringBootApplication(scanBasePackages = "org.example")
public class RecipeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipeServiceApplication.class, args);
    }

}
