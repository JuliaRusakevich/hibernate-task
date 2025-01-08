package ru.clevertec.service.impl;


import lombok.extern.slf4j.Slf4j;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.exception.CustomException;
import ru.clevertec.repository.impl.CarShowroomRepositoryImpl;
import ru.clevertec.service.api.BaseService;
import ru.clevertec.service.api.CarShowroomService;

import java.util.List;
import java.util.UUID;

@Slf4j
public class CarShowroomServiceImpl implements BaseService<UUID, CarShowroom>, CarShowroomService {

    private static final CarShowroomServiceImpl INSTANCE = new CarShowroomServiceImpl();

    final CarShowroomRepositoryImpl repository = CarShowroomRepositoryImpl.getInstance();

    @Override
    public CarShowroom add(CarShowroom carShowroom) {
        try {
            this.repository.create(carShowroom);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return carShowroom;
    }

    @Override
    public CarShowroom update(CarShowroom carShowroom) {
        try {
            this.repository.update(carShowroom);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return carShowroom;
    }

    @Override
    public CarShowroom findById(UUID uuid) {
        CarShowroom carShowroom = new CarShowroom();
        try {
            carShowroom = this.repository.findById(uuid);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return carShowroom;
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
    public List<CarShowroom> findAll() {
        return null;
    }

    @Override
    public void assignCarToShowroom(Car car, CarShowroom showroom) {
        try {
            this.repository.addCarToShowroom(car, showroom);
        } catch (CustomException e) {
            log.error(e.getMessage());
        }
    }

    public static CarShowroomServiceImpl getInstance() {
        return CarShowroomServiceImpl.INSTANCE;
    }
}
