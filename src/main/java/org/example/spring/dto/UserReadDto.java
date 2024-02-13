package org.example.spring.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.example.spring.database.entity.Role;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserReadDto {
    Long id;
    String username;
    String firstName;
    String lastName;
    LocalDate birthDate;
    Role role;
    CompanyReadDto company;
}
