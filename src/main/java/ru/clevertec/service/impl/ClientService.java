package ru.clevertec.service.impl;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.repository.impl.ClientRepository;
import ru.clevertec.service.api.IClientService;
import ru.clevertec.service.api.ICrudService;

import java.util.List;
import java.util.UUID;

public class ClientService implements ICrudService<UUID, Client>, IClientService {

    private static final ClientService INSTANCE = new ClientService();
    final ClientRepository repository = ClientRepository.getInstance();

    public Client add(Client client) {
        try {
            this.repository.create(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return client;
    }

    public Client update(Client client) {
        try {
            this.repository.update(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return client;
    }

    public Client findById(UUID uuid) {
        Client client = new Client();
        try {
            client = this.repository.findById(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return client;
    }

    public void delete(UUID uuid) {
        this.repository.delete(uuid);

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
            System.out.println(e.getMessage());
        }
    }

    public static ClientService getInstance() {
        return ClientService.INSTANCE;
    }
}
