package org.example.spring.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.config.conditional.PostgresConditional;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Conditional(PostgresConditional.class)
@Configuration
public class JpaConfiguration {
    @PostConstruct
    public void init() {
        log.info("Jpa configuration is enabled");
    }

//    @Bean
//    @ConfigurationProperties(prefix = "db")
//    public DatabaseProperties databaseProperties() {
//        return new DatabaseProperties();
//    }
}
