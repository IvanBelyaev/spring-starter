package org.example.spring.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserFilter {

    String firstName;

    String lastName;

    LocalDate birthDate;
}
