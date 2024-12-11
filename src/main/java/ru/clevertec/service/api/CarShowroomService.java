package ru.clevertec.service.api;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;

public interface CarShowroomService {

    void assignCarToShowroom(Car car, CarShowroom showroom);
}
