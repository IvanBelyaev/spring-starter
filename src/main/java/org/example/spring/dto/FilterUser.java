package org.example.spring.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class FilterUser {

    String firstName;

    String lastName;

    LocalDate birthDate;
}
