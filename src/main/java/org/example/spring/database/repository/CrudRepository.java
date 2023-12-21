package org.example.spring.database.repository;

import java.util.Optional;

public interface CrudRepository<K, E> {
    Optional<E> getById(K key);
    void delete(E entity);
}
