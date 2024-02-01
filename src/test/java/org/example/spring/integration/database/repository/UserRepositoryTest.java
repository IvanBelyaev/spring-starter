package org.example.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.example.spring.database.entity.Role;
import org.example.spring.database.entity.User;
import org.example.spring.database.querydsl.QPredicate;
import org.example.spring.database.repository.UserRepository;
import org.example.spring.dto.FilterUser;
import org.example.spring.integration.annotation.IT;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.spring.database.entity.QUser.user;

@IT
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void findAllBy() {
        var users = userRepository.findAllBy("a", "ov");
        assertThat(users).hasSize(3);
    }

    @Test
    void findByUsername() {
        var username = "ivan@gmail.com";

        var maybeUser = userRepository.findByUsername(username);

        assertThat(maybeUser).isPresent();
        maybeUser.ifPresent(user ->
                assertThat(user.getUsername()).isEqualTo(username));
    }

    @Test
    void updateRole() {
        var ivan = userRepository.getReferenceById(1L);
        assertThat(ivan.getRole()).isEqualTo(Role.ADMIN);

        ivan.setBirthDate(LocalDate.now());

        var count = userRepository.updateRole(Role.USER, 1L, 5L);
        assertThat(count).isEqualTo(2);

//        ivan.getCompany().getName();

        var theSameIvan = userRepository.getReferenceById(1L);
        assertThat(theSameIvan.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void checkPageable() {
        var sort = Sort.sort(User.class).by(User::getFirstName)
                .and(Sort.sort(User.class).by(User::getLastName));
        var pageable = PageRequest.of(1, 2, sort);

        var users = userRepository.findAllBy(pageable);

        assertThat(users.stream().map(User::getUsername)).containsExactly("petr@gmail.com", "sveta@gmail.com");
    }

    @Test
    void checkPage() {
        var pageable = PageRequest.of(1, 2, Sort.by("id"));

        var page = userRepository.findAllBy(pageable);

        page.getContent().forEach(user -> System.out.println(user.getCompany().getName()));
        while (page.hasNext()) {
            page = userRepository.findAllBy(page.nextPageable());
            page.getContent().forEach(user -> System.out.println(user.getCompany().getName()));
        }
    }

    @Test
    void checkTop3() {
        var users = userRepository.findTop3ByOrderByUsername();
        assertThat(users).hasSize(3);
    }

    @Test
    void checkProjections() {
        var users = userRepository.findAllByCompanyId(1);
        assertThat(users).hasSize(2);
    }

    @Test
    void checkCustomImplementations() {
        var filter = FilterUser.builder()
                .lastName("ov")
                .birthDate(LocalDate.now().plusDays(1))
                .build();
        var users = userRepository.findAllByFilter(filter);
        assertThat(users).hasSize(4);
    }

    @Test
    @Commit
    void checkAuditing() {
        var maybeUser = userRepository.findById(1L);
        maybeUser.ifPresent(user -> user.setBirthDate(LocalDate.now()));
        userRepository.flush();
        System.out.println();
    }

    @Test
    void checkQuerydslPredicateExecutor() {
        var predicate = QPredicate.builder()
                .add("ov", user.lastName::containsIgnoreCase)
                .add(LocalDate.now().plusDays(1), user.birthDate::before)
                .build();

        var users = userRepository.findAll(predicate);
        assertThat(users).hasSize(4);
    }
}