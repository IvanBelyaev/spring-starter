package org.example.spring.service;

import lombok.RequiredArgsConstructor;
import org.example.spring.database.entity.Company;
import org.example.spring.database.repository.CrudRepository;
import org.example.spring.dto.CompanyReadDto;
import org.example.spring.listener.event.AccessType;
import org.example.spring.listener.event.AccessTypeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final UserService userService;
    private final CrudRepository<Long, Company> companyRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Optional<CompanyReadDto> findById(Long id) {
        return companyRepository.getById(id)
                .map(company -> {
                    eventPublisher.publishEvent(new AccessTypeEvent(company, AccessType.UPDATE));
                    return new CompanyReadDto(company.id());
                });
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }
}
