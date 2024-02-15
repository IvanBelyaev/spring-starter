package org.example.spring.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.spring.dto.UserCreateEditDto;
import org.example.spring.validation.UserInfo;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.hasText;

@Component
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto> {

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        return hasText(value.getFirstName()) || hasText(value.getLastName());
    }
}
