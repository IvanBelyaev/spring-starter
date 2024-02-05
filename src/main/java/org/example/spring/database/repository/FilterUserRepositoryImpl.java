package org.example.spring.database.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.spring.database.entity.Role;
import org.example.spring.database.entity.User;
import org.example.spring.database.querydsl.QPredicate;
import org.example.spring.dto.FilterUser;
import org.example.spring.dto.PersonalInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.example.spring.database.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String FIND_BY_COMPANY_AND_ROLE = """
            SELECT
                firstname,
                lastname,
                birth_date
            FROM users
            WHERE company_id = ?
                AND role = ?
            """;

    private static final String UPDATE_COMPANY_AND_ROLE = """
            UPDATE
                users
            SET
                company_id = ?,
                role = ?
            WHERE
                id = ?
            
            """;

    private static final String UPDATE_COMPANY_AND_ROLE_NAMED = """
            UPDATE
                users
            SET
                company_id = :companyId,
                role = :role
            WHERE
                id = :id
            
            """;

    @Override
    public List<User> findAllByFilter(FilterUser filterUser) {
//        var cb = entityManager.getCriteriaBuilder();
//        var criteria = cb.createQuery(User.class);
//        var user = criteria.from(User.class);
//        criteria.select(user);
//
//        List<Predicate> predicates = new ArrayList<>();
//        if (filterUser.getFirstName() != null) {
//            predicates.add(cb.like(user.get("firstName"), filterUser.getFirstName()));
//        }
//        if (filterUser.getLastName() != null) {
//            predicates.add(cb.like(user.get("lastName"), filterUser.getLastName()));
//        }
//        if (filterUser.getBirthDate() != null) {
//            predicates.add(cb.lessThan(user.get("birthDate"), filterUser.getBirthDate()));
//        }
//        criteria.where(predicates.toArray(Predicate[]::new));
//
//        return entityManager.createQuery(criteria).getResultList();

        var predicate = QPredicate.builder()
                .add(filterUser.getFirstName(), user.firstName::containsIgnoreCase)
                .add(filterUser.getLastName(), user.lastName::containsIgnoreCase)
                .add(filterUser.getBirthDate(), user.birthDate::before)
                .build();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE,
                (rs, rowNum) ->
                    new PersonalInfo(
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getDate("birth_date").toLocalDate()
                    ),
                companyId, role.name()
        );
    }

    @Override
    public void updateCompanyAndRole(List<User> users) {
        var batchArgs = users.stream()
                .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
                .toList();
        jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, batchArgs);
    }

    @Override
    public void updateCompanyAndRoleNamed(List<User> users) {
        var batchArgs = users.stream()
                .map(user -> Map.of(
                        "id", user.getId(),
                        "companyId", user.getCompany().getId(),
                        "role", user.getRole().name()
                ))
                .map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED, batchArgs);
    }
}
