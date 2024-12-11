package ru.clevertec.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.exception.CustomException;
import ru.clevertec.repository.impl.ClientRepositoryImpl;
import ru.clevertec.service.api.BaseService;
import ru.clevertec.service.api.ClientService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ClientServiceImpl implements BaseService<UUID, Client>, ClientService {

    private static final ClientServiceImpl INSTANCE = new ClientServiceImpl();
    final ClientRepositoryImpl repository = ClientRepositoryImpl.getInstance();

    public Client add(Client client) {
        try {
            this.repository.create(client);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return client;
    }

    public Client update(Client client) {
        try {
            this.repository.update(client);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return client;
    }

    public Client findById(UUID uuid) {
        Client client = new Client();
        try {
            client = this.repository.findById(uuid);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return client;
    }

    public void delete(UUID uuid) {
        try {
            this.repository.delete(uuid);
        } catch (CustomException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public void buyCar(Client client, Car car) {
        try {
            this.repository.addCarToClient(client, car);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    public List<Car> findCarsByClientUuid(UUID clientUuid) {
        List<Car> cars = new ArrayList<>();
        try {
            cars = this.repository.findCarsByClientUUID(clientUuid);
        } catch (CustomException e) {
            log.error(e.getMessage());
        }
        return cars;
    }

    public static ClientServiceImpl getInstance() {
        return ClientServiceImpl.INSTANCE;
    }
}
