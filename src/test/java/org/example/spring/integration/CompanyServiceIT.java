package org.example.spring.integration;

import org.example.spring.config.DatabaseProperties;
import org.example.spring.dto.CompanyReadDto;
import org.example.spring.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class CompanyServiceIT {
    private static final Long COMPANY_ID = 1L;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private DatabaseProperties databaseProperties;

    @Test
    void findById() {
        Optional<CompanyReadDto> maybeCompany = companyService.findById(COMPANY_ID);

        assertThat(maybeCompany).isPresent();
        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);
        maybeCompany.ifPresent(companyReadDto -> assertThat(companyReadDto).isEqualTo(expectedResult));
    }
}
