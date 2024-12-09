package ru.clevertec.service.api;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;

public interface ICarShowroomService {

    void assignCarToShowroom(Car car, CarShowroom showroom);
}
