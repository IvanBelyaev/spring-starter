package org.example.spring.integration;

import lombok.RequiredArgsConstructor;
import org.example.spring.dto.UserCreateEditDto;
import org.example.spring.http.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.spring.dto.PageResponse.Fields.content;
import static org.example.spring.dto.UserCreateEditDto.Fields.*;
import static org.example.spring.dto.UserReadDto.Fields.username;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .andExpect(model().attribute("users", hasProperty(content, hasSize(5))));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/" + FIRST_USER_ID))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/user"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", hasProperty(username, equalTo(FIRST_USER_USERNAME))));
    }

    @Test
    void findById_userNotFound() throws Exception {
        mockMvc.perform(get("/users/" + -123))
                .andExpect(view().name("error/error500"));
    }

    @Test
    void create() throws Exception {
        var mockMultipartFile =
                new MockMultipartFile("image", "test.png", "image/png", "test".getBytes());
        mockMvc.perform(multipart("/users")
                        .file(mockMultipartFile)
                        .param(UserCreateEditDto.Fields.username, "test@gmail.com")
                        .param(firstName, "some first name")
                        .param(lastName, "some last name")
                        .param(role, "ADMIN")
                        .param(companyId, "1")
                        .param(birthDate, "2000-05-15")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")
                );
    }

    @Test
    void update() throws Exception {
        var mockMultipartFile =
                new MockMultipartFile("image", "test.png", "image/png", "test".getBytes());
        mockMvc.perform(multipart("/users/" + FIRST_USER_ID + "/update")
                        .file(mockMultipartFile)
                        .param(UserCreateEditDto.Fields.username, "test@gmail.com")
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