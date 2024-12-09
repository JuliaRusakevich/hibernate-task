package ru.clevertec.repository.api;

import java.util.List;

public interface ICRUDRepository<I, E> {

    E create(E e);

    E update(E e);

    E findById(I i);

    void delete(I i);

    List<E> findAll();
}
