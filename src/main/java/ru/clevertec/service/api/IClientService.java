package ru.clevertec.service.api;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;

public interface IClientService {

   void buyCar(Client client, Car car);

}
