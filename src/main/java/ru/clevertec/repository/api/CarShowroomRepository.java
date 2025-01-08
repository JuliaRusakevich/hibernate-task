package ru.clevertec.repository.api;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;

public interface CarShowroomRepository {

    void addCarToShowroom(Car car, CarShowroom showroom);
}
