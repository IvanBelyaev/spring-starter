package org.example.spring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.example.spring.database.entity.Role;
import org.example.spring.validation.UserInfo;
import org.example.spring.validation.group.CreateAction;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Value
@FieldNameConstants
@UserInfo(groups = CreateAction.class)
public class UserCreateEditDto {
    @Email(message = "поле Username {jakarta.validation.constraints.Email.message}")
    String username;

    @NotBlank(groups = CreateAction.class)
    String rawPassword;

    @Size(min = 3, max = 64, message = "поле FirstName, {jakarta.validation.constraints.Size.message}")
    String firstName;

    String lastName;

    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    Role role;

    Integer companyId;

    MultipartFile image;
}
