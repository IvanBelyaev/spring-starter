package org.example.spring.database.repository;

import org.example.spring.database.entity.Role;
import org.example.spring.database.entity.User;
import org.example.spring.dto.UserFilter;
import org.example.spring.dto.PersonalInfo;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter userFilter);

    List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);

    void updateCompanyAndRole(List<User> users);

    void updateCompanyAndRoleNamed(List<User> users);
}
