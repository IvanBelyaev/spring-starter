package org.example.spring.database.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.spring.database.entity.User;
import org.example.spring.database.querydsl.QPredicate;
import org.example.spring.dto.FilterUser;

import java.util.List;

import static org.example.spring.database.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;

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
}
