package org.example.spring.dto;

import lombok.Value;
import org.example.spring.database.entity.Role;

import java.time.LocalDate;

@Value
public class UserCreateEditDto {
    Long id;
    String username;
    String firstName;
    String lastName;
    LocalDate birthDate;
    Role role;
    Integer companyId;
}
