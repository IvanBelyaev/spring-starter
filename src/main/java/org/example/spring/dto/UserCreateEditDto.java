package org.example.spring.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.example.spring.database.entity.Role;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserCreateEditDto {
    Long id;
    String username;
    String firstName;
    String lastName;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;
    Role role;
    Integer companyId;
}
