package ru.clevertec.repository.api;

import ru.clevertec.exception.CustomException;

import java.util.List;

public interface CrudRepository<I, E> {

    E create(E e);

    E update(E e);

    E findById(I i);

    void delete(I i);

    List<E> findAll();
}
