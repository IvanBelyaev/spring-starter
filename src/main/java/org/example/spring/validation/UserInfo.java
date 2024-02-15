package org.example.spring.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.spring.validation.impl.UserInfoValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UserInfoValidator.class)
public @interface UserInfo {

    String message() default "Должно быть заполнено хотя бы одно из полей: FirstName, LastName";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
