package ru.clevertec.repository.api;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;

import java.util.List;
import java.util.UUID;

public interface IClientRepository {

    void addCarToClient(Client client, Car car);

    List<Car> findCarsByClientUUID(UUID clientUUID);
}
