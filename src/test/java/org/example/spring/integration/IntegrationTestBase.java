package org.example.spring.integration;

import org.example.spring.integration.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
@Sql(value = {
        "classpath:sql/data.sql"
})
@WithMockUser(username = "testUser@gmail.com", password = "test", authorities = {"USER", "ADMIN"})
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> testContainer = new PostgreSQLContainer<>("postgres:16.1");

    @BeforeAll
    public static void runContainer() {
        testContainer.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", testContainer::getJdbcUrl);
    }
}
