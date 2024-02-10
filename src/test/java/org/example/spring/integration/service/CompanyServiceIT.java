package org.example.spring.integration.service;

import lombok.RequiredArgsConstructor;
import org.example.spring.config.DatabaseProperties;
import org.example.spring.dto.CompanyReadDto;
import org.example.spring.integration.IntegrationTestBase;
import org.example.spring.service.CompanyService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CompanyServiceIT extends IntegrationTestBase {
    private static final Integer COMPANY_ID = 1;

    private final CompanyService companyService;
    private final DatabaseProperties databaseProperties;

    @Test
    void findById() {
        Optional<CompanyReadDto> maybeCompany = companyService.findById(COMPANY_ID);

        assertThat(maybeCompany).isPresent();
        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID, null);
        maybeCompany.ifPresent(companyReadDto -> assertThat(companyReadDto).isEqualTo(expectedResult));
    }
}
