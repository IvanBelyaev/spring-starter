package org.example.spring.integration.database.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.spring.database.entity.Company;
import org.example.spring.database.repository.CompanyRepository;
import org.example.spring.integration.IntegrationTestBase;
import org.example.spring.integration.annotation.IT;
import org.example.spring.listener.CustomTestExecutionListener;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
//@Commit
@TestExecutionListeners(
        listeners = CustomTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class CompanyRepositoryTest extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;
    private final CompanyRepository companyRepository;

    @Test
    void testQueries() {
        companyRepository.findByName("Google");
        companyRepository.findByNameContainingIgnoreCase("e");
    }

    @Test
    void delete() {
        var apple = Company.builder()
                .name("Apple")
                .build();
        entityManager.persist(apple);
        entityManager.flush();
        entityManager.clear();
        assertNotNull(apple.getId());

        var maybeCompany = companyRepository.findById(apple.getId());
        assertThat(maybeCompany).isPresent();
        maybeCompany.ifPresent(companyRepository::delete);
        entityManager.flush();

        assertThat(companyRepository.findById(apple.getId())).isEmpty();
    }


    @Test
    void findById() {
        transactionTemplate.executeWithoutResult(tx -> {
            var company = entityManager.find(Company.class, 1);
            assertNotNull(company);
            assertThat(company.getLocales()).hasSize(2);
        });
    }

    @Test
    void save() {
        var apple = Company.builder()
                .name("Apple")
                .build();
        entityManager.persist(apple);
        assertNotNull(apple.getId());
    }
}