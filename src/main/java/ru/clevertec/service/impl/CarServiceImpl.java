package ru.clevertec.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.entity.Car;
import ru.clevertec.exception.CustomException;
import ru.clevertec.repository.impl.CarRepositoryImpl;
import ru.clevertec.service.api.BaseService;
import ru.clevertec.service.api.CarService;
import ru.clevertec.validator.ValidationResult;
import ru.clevertec.validator.ValidatorImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
public class CarServiceImpl implements BaseService<UUID, Car>, CarService {

    private static final CarServiceImpl INSTANCE = new CarServiceImpl();
    final CarRepositoryImpl repository = CarRepositoryImpl.getInstance();
    final ValidatorImpl validator = ValidatorImpl.getInstance();

    @Override
    public Car add(Car car) {
        try {
            this.repository.create(car);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return car;
    }

    @Override
    public Car update(Car car) {
        try {
            this.repository.update(car);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return car;
    }

    @Override
    public Car findById(UUID uuid) {
        Car car = new Car();
        try {
            car = this.repository.findById(uuid);
        } catch (CustomException e) {
            log.error(e.getMessage());
        }
        return car;

    }

    @Override
    public void delete(UUID uuid) {
        try {
            this.repository.delete(uuid);
        } catch (CustomException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        try {
            this.repository.findAll();
        } catch (CustomException e) {
            log.error(e.getMessage());
        }
        return cars;
    }

    @Override
    public List<Car> findCarsSortedByPrice(String typeSorting) {
        List<Car> cars = new ArrayList<>();
        try {
            cars = this.repository.findCarsSortedByPrice(typeSorting);
        } catch (CustomException e) {
            log.error(e.getMessage());
        }
        return cars;
    }

    @Override
    public List<Car> findCarsByFilters(String make, String category, Integer year, BigDecimal minPrice, BigDecimal maxPrice) {
        ValidationResult validationResult = validator.checkFilterParams(make, category, year);
        List<Car> cars = new ArrayList<>();
        if (validationResult.isValid()) {
            cars = this.repository.findCarsByFilters(make, category, year, minPrice, maxPrice);

        } else {
            log.error(validationResult.getErrors().toString());
        }
        return cars;
    }

    public static CarServiceImpl getInstance() {
        return CarServiceImpl.INSTANCE;
    }


}

