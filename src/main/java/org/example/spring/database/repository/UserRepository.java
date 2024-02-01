package org.example.spring.database.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.example.spring.database.entity.Role;
import org.example.spring.database.entity.User;
import org.example.spring.dto.PersonalInfoIntf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        RevisionRepository<User, Long, Integer>,
        QuerydslPredicateExecutor<User> {

    @Query("select u from User u where u.firstName like %:firstName% and u.lastName like %:lastName%")
    List<User> findAllBy(String firstName, String lastName);

    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username", nativeQuery = true)
    Optional<User> findByUsername(String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u set u.role = :role where u.id in (:ids)")
    int updateRole(Role role, Long... ids);

//    @EntityGraph("User.company")
    @EntityGraph(attributePaths = {"company", "company.locales"})
    @Query(
            value = "select u from User u",
            countQuery = "select count(distinct u.firstName) from User u")
    Page<User> findAllBy(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
    List<User> findTop3ByOrderByUsername();

//    List<PersonalInfo> findAllByCompanyId(int companyId);

//    <T> List<T> findAllByCompanyId(int companyId, Class<T> clazz);

    @Query(value =
            "SELECT u.firstname, u.lastname, u.birth_date birthDate " +
            "FROM users u " +
            "WHERE u.company_id = :companyId",
            nativeQuery = true)
    List<PersonalInfoIntf> findAllByCompanyId(int companyId);
}
