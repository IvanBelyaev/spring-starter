package org.example.spring.integration;

import lombok.RequiredArgsConstructor;
import org.example.spring.dto.UserReadDto;
import org.example.spring.http.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.spring.dto.UserCreateEditDto.Fields.birthDate;
import static org.example.spring.dto.UserCreateEditDto.Fields.companyId;
import static org.example.spring.dto.UserCreateEditDto.Fields.firstName;
import static org.example.spring.dto.UserCreateEditDto.Fields.lastName;
import static org.example.spring.dto.UserCreateEditDto.Fields.role;
import static org.example.spring.dto.UserCreateEditDto.Fields.username;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private static final Long FIRST_USER_ID = 1L;
    private static final String FIRST_USER_USERNAME = "ivan@gmail.com";
    private final MockMvc mockMvc;
    private final UserController userController;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(5)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/" + FIRST_USER_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/user"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute(
                        "user", hasProperty(UserReadDto.Fields.username, equalTo(FIRST_USER_USERNAME))));
    }

    @Test
    void findById_userNotFound() throws Exception {
        mockMvc.perform(get("/users/" + -123))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(username, "test@gmail.com")
                        .param(firstName, "some first name")
                        .param(lastName, "some last name")
                        .param(role, "ADMIN")
                        .param(companyId, "1")
                        .param(birthDate, "2000-05-15")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/users/" + FIRST_USER_ID + "/update")
                        .param(username, "test@gmail.com")
                        .param(firstName, "some first name")
                        .param(lastName, "some last name")
                        .param(role, "ADMIN")
                        .param(companyId, "1")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/" + FIRST_USER_ID + "/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")

                );
    }
}