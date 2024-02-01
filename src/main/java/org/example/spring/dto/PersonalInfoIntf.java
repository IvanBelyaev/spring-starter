package org.example.spring.dto;

import org.springframework.beans.factory.annotation.Value;

public interface PersonalInfoIntf {

    String getFirstName();

    String getLastName();

    String getBirthDate();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
}
