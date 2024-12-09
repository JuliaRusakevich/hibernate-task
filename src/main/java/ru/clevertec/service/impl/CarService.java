package ru.clevertec.service.impl;

import ru.clevertec.entity.Car;
import ru.clevertec.repository.impl.CarRepository;
import ru.clevertec.service.api.ICarService;
import ru.clevertec.service.api.ICrudService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public class CarService implements ICrudService<UUID, Car>, ICarService {

    private static final CarService INSTANCE = new CarService();
    final CarRepository repository = CarRepository.getInstance();

    @Override
    public Car add(Car car) {
        try {
            this.repository.create(car);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return car;
    }

    @Override
    public Car update(Car car) {
        try {
            this.repository.update(car);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return car;
    }

    @Override
    public Car findById(UUID uuid) {
        return this.repository.findById(uuid);

    }

    @Override
    public void delete(UUID uuid) {
        this.repository.delete(uuid);

    }

    @Override
    public List<Car> findAll() {
        return this.repository.findAll();
    }

    @Override
    public List<Car> findCarsSortedByPrice(String typeSorting) {
        return this.repository.findCarsSortedByPrice(typeSorting);
    }

    @Override
    public List<Car> findCarsByFilters(String make, String category, Integer year, BigDecimal minPrice, BigDecimal maxPrice) {
        return this.repository.findCarsByFilters(make, category, year, minPrice, maxPrice);
    }

    public static CarService getInstance() {
        return CarService.INSTANCE;
    }
}

