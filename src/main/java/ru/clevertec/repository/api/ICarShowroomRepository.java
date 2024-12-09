package ru.clevertec.repository.api;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;

public interface ICarShowroomRepository {

    void addCarToShowroom(Car car, CarShowroom showroom);
}
