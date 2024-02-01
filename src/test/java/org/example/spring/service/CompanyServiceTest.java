package org.example.spring.service;

import org.example.spring.database.entity.Company;
import org.example.spring.database.repository.CompanyRepository;
import org.example.spring.dto.CompanyReadDto;
import org.example.spring.listener.event.AccessTypeEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({
     MockitoExtension.class
})
class CompanyServiceTest {
    private static final Integer COMPANY_ID = 1;

    @Mock
    private UserService userService;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById() {
        doReturn(Optional.of(new Company(COMPANY_ID, null, emptyMap()))).when(companyRepository).findById(COMPANY_ID);

        Optional<CompanyReadDto> maybeCompany = companyService.findById(COMPANY_ID);

        assertThat(maybeCompany).isPresent();
        CompanyReadDto expectedResult = new CompanyReadDto(COMPANY_ID);
        maybeCompany.ifPresent(companyReadDto -> assertThat(companyReadDto).isEqualTo(expectedResult));

        verify(eventPublisher).publishEvent(any(AccessTypeEvent.class));
        verifyNoMoreInteractions(userService, eventPublisher, companyRepository);
    }

    @Test
    void delete() {
        doNothing().when(companyRepository).delete(new Company(COMPANY_ID, null, emptyMap()));

        companyService.delete(new Company(COMPANY_ID, null, emptyMap()));

        verifyNoMoreInteractions(userService, eventPublisher, companyRepository);
    }
}