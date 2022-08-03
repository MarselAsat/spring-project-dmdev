package com.dmdev.spring.dao;

import java.util.Optional;

public interface CrudRepository<K, E> {
    Optional<E> findById(K id);

    void delete(K id);
}
