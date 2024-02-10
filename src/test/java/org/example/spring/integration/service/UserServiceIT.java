package org.example.spring.integration.service;

import lombok.RequiredArgsConstructor;
import org.example.spring.database.entity.Role;
import org.example.spring.dto.UserCreateEditDto;
import org.example.spring.integration.IntegrationTestBase;
import org.example.spring.service.UserService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private final UserService userService;
    private static final Long USER_ID = 1L;
    private static final Integer COMPANY_ID = 1;
    private static final Long NEXT_USER_ID = 6L;

    @Test
    void findAll() {
        var users = userService.findAll();
        assertThat(users).hasSize(5);
    }

    @Test
    void findById() {
        var maybeUser = userService.findById(USER_ID);
        assertThat(maybeUser).isPresent();
        maybeUser.ifPresent(user -> assertThat(user.getUsername()).isEqualTo("ivan@gmail.com"));
    }

    @Test
    void create() {
        var userCreateEditDto = new UserCreateEditDto(
                null,
                "test",
                "test",
                "test",
                LocalDate.now(),
                Role.ADMIN,
                COMPANY_ID
        );
        var userReadDto = userService.create(userCreateEditDto);

        assertThat(userReadDto.getId()).isEqualTo(NEXT_USER_ID);
        assertThat(userReadDto.getUsername()).isEqualTo(userCreateEditDto.getUsername());
        assertThat(userReadDto.getFirstName()).isEqualTo(userCreateEditDto.getFirstName());
        assertThat(userReadDto.getLastName()).isEqualTo(userCreateEditDto.getLastName());
        assertThat(userReadDto.getBirthDate()).isEqualTo(userCreateEditDto.getBirthDate());
        assertThat(userReadDto.getRole()).isSameAs(userCreateEditDto.getRole());
        assertThat(userReadDto.getCompany().id()).isEqualTo(userCreateEditDto.getCompanyId());
    }

    @Test
    void create_fail() {
        UserCreateEditDto userCreateEditDto = null;
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> userService.create(userCreateEditDto));
    }

    @Test
    void update() {
        var userCreateEditDto = new UserCreateEditDto(
                USER_ID,
                "test",
                "test",
                "test",
                LocalDate.now(),
                Role.ADMIN,
                COMPANY_ID
        );
        var maybeUser = userService.update(USER_ID, userCreateEditDto);

        assertThat(maybeUser).isPresent();
        maybeUser.ifPresent(user -> {
            assertThat(user.getId()).isEqualTo(USER_ID);
            assertThat(user.getUsername()).isEqualTo(userCreateEditDto.getUsername());
            assertThat(user.getFirstName()).isEqualTo(userCreateEditDto.getFirstName());
            assertThat(user.getLastName()).isEqualTo(userCreateEditDto.getLastName());
            assertThat(user.getBirthDate()).isEqualTo(userCreateEditDto.getBirthDate());
            assertThat(user.getRole()).isSameAs(userCreateEditDto.getRole());
            assertThat(user.getCompany().id()).isEqualTo(userCreateEditDto.getCompanyId());
        });
    }

    @Test
    void delete() {
        assertThat(userService.delete(USER_ID)).isEqualTo(true);
        assertThat(userService.delete(-123L)).isEqualTo(false);
    }
}
