package ru.clevertec.service.impl;


import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.repository.impl.CarShowroomRepository;
import ru.clevertec.service.api.ICarShowroomService;
import ru.clevertec.service.api.ICrudService;

import java.util.List;
import java.util.UUID;

public class CarShowroomService implements ICrudService<UUID, CarShowroom>, ICarShowroomService {

    private static final CarShowroomService INSTANCE = new CarShowroomService();

    final CarShowroomRepository repository = CarShowroomRepository.getInstance();

    @Override
    public CarShowroom add(CarShowroom carShowroom) {
        try {
            this.repository.create(carShowroom);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return carShowroom;
    }

    @Override
    public CarShowroom update(CarShowroom carShowroom) {
        try {
            this.repository.update(carShowroom);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return carShowroom;
    }

    @Override
    public CarShowroom findById(UUID uuid) {
        CarShowroom carShowroom = new CarShowroom();
        try {
            carShowroom = this.repository.findById(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return carShowroom;
    }

    @Override
    public void delete(UUID uuid) {
        this.repository.delete(uuid);
    }

    @Override
    public List<CarShowroom> findAll() {
        return null;
    }

    @Override
    public void assignCarToShowroom(Car car, CarShowroom showroom) {
        this.repository.addCarToShowroom(car, showroom);
    }

    public static CarShowroomService getInstance() {
        return CarShowroomService.INSTANCE;
    }
}
