package org.example.spring.database.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.bpp.Auditing;
import org.example.spring.bpp.Transaction;
import org.example.spring.database.entity.Company;
import org.example.spring.database.pool.ConnectionPool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@Auditing
@Transaction
@RequiredArgsConstructor
public class UserRepository implements CrudRepository<Long, Company> {

    @Qualifier("pool1")
    private final ConnectionPool pool1;

    @Override
    public Optional<Company> getById(Long key) {
        log.info("get byId");
        return Optional.of(new Company(key));
    }

    @Override
    public void delete(Company entity) {
        log.warn("delete entity");
    }
}
