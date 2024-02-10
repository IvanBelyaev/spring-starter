package org.example.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.example.spring.database.entity.User;
import org.example.spring.dto.UserReadDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto map(User object) {
        var company = Optional.ofNullable(object.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getFirstName(),
                object.getLastName(),
                object.getBirthDate(),
                object.getRole(),
                company
        );
    }
}
