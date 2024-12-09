package ru.clevertec.service.api;

import java.util.List;


public interface ICrudService<I, E> {
    E add(E e);

    E update(E e);

    E findById(I i);

    void delete(I i);

    List<E> findAll();
}

