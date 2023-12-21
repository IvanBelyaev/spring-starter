package org.example.spring;

import org.example.spring.config.DatabaseProperties;
import org.example.spring.database.entity.Company;
import org.example.spring.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        context.getBean(UserService.class).findById(1L);
        DatabaseProperties bean = context.getBean(DatabaseProperties.class);
        context.getBean(UserService.class).delete(new Company(1L));
        System.out.println();
    }
}
