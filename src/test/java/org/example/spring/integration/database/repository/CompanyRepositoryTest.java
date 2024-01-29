package org.example.spring.integration.database.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.spring.database.entity.Company;
import org.example.spring.integration.annotation.IT;
import org.example.spring.listener.CustomTestExecutionListener;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IT
@RequiredArgsConstructor
//@Commit
@TestExecutionListeners(
        listeners = CustomTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class CompanyRepositoryTest {

    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;

    @Test
    void findById() {
        transactionTemplate.executeWithoutResult(tx -> {
            var company = entityManager.find(Company.class, 1);
            assertNotNull(company);
            assertThat(company.getLocales()).hasSize(2);
        });
    }

    @Test
    public void save() {
        var apple = Company.builder()
                .name("Apple")
                .build();
        entityManager.persist(apple);
        assertNotNull(apple.getId());
    }
}