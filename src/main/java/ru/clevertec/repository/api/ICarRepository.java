package ru.clevertec.repository.api;

import ru.clevertec.entity.Car;

import java.math.BigDecimal;
import java.util.List;

public interface ICarRepository {

    List<Car> findCarsSortedByPrice(String typeSorting);

    List<Car> findCarsByFilters(String make, String category, Integer year, BigDecimal minPrice, BigDecimal maxPrice);
}
