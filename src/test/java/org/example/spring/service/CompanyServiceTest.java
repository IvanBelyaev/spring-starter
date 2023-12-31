package org.example.spring.service;

import org.example.spring.database.entity.Company;
import org.example.spring.database.repository.CrudRepository;
import org.example.spring.dto.CompanyReadDto;
import org.example.spring.listener.event.AccessTypeEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({
     MockitoExtension.class
})
class CompanyServiceTest {
    private static final Long COMPANY_ID = 1L;

    @Mock
    private UserService userService;
    @Mock
    private CrudRepository<Long, Company> companyRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById() {
        doReturn(Optional.of(new Company(COMPANY_ID))).when(companyRepository).getById(COMPANY_ID);

        Optional<CompanyReadDto> maybeCompany = companyService.findById(COMPANY_ID);

        assertThat(maybeCompany).isPresent();
        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);
        maybeCompany.ifPresent(companyReadDto -> assertThat(companyReadDto).isEqualTo(expectedResult));

        verify(eventPublisher).publishEvent(any(AccessTypeEvent.class));
        verifyNoMoreInteractions(userService, eventPublisher, companyRepository);
    }

    @Test
    void delete() {
        doNothing().when(companyRepository).delete(new Company(COMPANY_ID));

        companyService.delete(new Company(COMPANY_ID));

        verifyNoMoreInteractions(userService, eventPublisher, companyRepository);
    }
}