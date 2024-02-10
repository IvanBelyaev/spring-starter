package org.example.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.example.spring.database.entity.Company;
import org.example.spring.database.entity.User;
import org.example.spring.database.repository.CompanyRepository;
import org.example.spring.dto.UserCreateEditDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final CompanyRepository companyRepository;

    @Override
    public User map(UserCreateEditDto object) {
        var user = new User();
        copy(object, user);
        return user;
    }

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setId(object.getId());
        user.setUsername(object.getUsername());
        user.setFirstName(object.getFirstName());
        user.setLastName(object.getLastName());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setCompany(getCompany(object.getCompanyId()));
    }

    private Company getCompany(Integer companyId) {
        return  Optional.ofNullable(companyId)
                .flatMap(companyRepository::findById)
                .orElse(null);
    }
}
