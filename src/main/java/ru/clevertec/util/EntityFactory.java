package ru.clevertec.util;

import lombok.experimental.UtilityClass;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.entity.Category;
import ru.clevertec.entity.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@UtilityClass
public class EntityFactory {

    public List<Car> generateCars() {
        List<Car> cars = new ArrayList<>();

        Car car = Car.builder()
                .uuid(UUID.randomUUID())
                .model("M5 (sedan)")
                .make("BMW")
                .year(2024)
                .price(BigDecimal.valueOf(56200))
                .build();

        Car car1 = Car.builder()
                .uuid(UUID.randomUUID())
                .model("740d xDrive Sedan")
                .make("BMW")
                .year(2024)
                .price(BigDecimal.valueOf(129000))
                .build();

        Car car2 = Car.builder()
                .uuid(UUID.randomUUID())
                .model("540d xDrive Sedan")
                .make("BMW")
                .year(2024)
                .price(BigDecimal.valueOf(40500))
                .build();

        Car car3 = Car.builder()
                .uuid(UUID.randomUUID())
                .model("M4 Competition M xDrive Coup")
                .make("BMW")
                .year(2023)
                .price(BigDecimal.valueOf(50500))
                .build();

        Car car4 = Car.builder()
                .uuid(UUID.randomUUID())
                .model("420i Coup")
                .make("BMW")
                .year(2022)
                .price(BigDecimal.valueOf(30500))
                .build();

        Car car5 = Car.builder()
                .uuid(UUID.randomUUID())
                .model("J7 (sedan)")
                .make("JAC")
                .year(2024)
                .price(BigDecimal.valueOf(60500))
                .build();

        Car car6 = Car.builder()
                .uuid(UUID.randomUUID())
                .model("T8 Pro (sedan)")
                .make("JAC")
                .year(2024)
                .price(BigDecimal.valueOf(80500))
                .build();

        cars.add(car);
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cars.add(car4);
        cars.add(car5);
        cars.add(car6);

        return cars;

    }

    public List<CarShowroom> generateShowrooms() {
        List<CarShowroom> carShowrooms = new ArrayList<>();

        CarShowroom carShowroom = CarShowroom.builder()
                .uuid(UUID.randomUUID())
                .name("АВТОИДЕЯ — официальный дилер BMW и MINI в Беларуси")
                .address("МКАД, Минский район, д. Цна, ул. Юбилейная, д. 4")
                .build();

        CarShowroom carShowroom1 = CarShowroom.builder()
                .uuid(UUID.randomUUID())
                .name("JAC Атлант-М Боровая")
                .address("р-н д. Боровая, д. 2")
                .build();

        carShowrooms.add(carShowroom);
        carShowrooms.add(carShowroom1);

        return carShowrooms;
    }

    public List<Category> generateCategories() {
        List<Category> categories = new ArrayList<>();

        Category category = Category.builder()
                .uuid(UUID.randomUUID())
                .body("Sedan")
                .build();

        Category category1 = Category.builder()
                .uuid(UUID.randomUUID())
                .body("Coup")
                .build();

        Category category2 = Category.builder()
                .uuid(UUID.randomUUID())
                .body("Hatchback")
                .build();

        categories.add(category);
        categories.add(category1);
        categories.add(category2);

        return categories;
    }

    public Client generateClient() {
        Set<String> contacts = new HashSet<>();
        contacts.add("+375336500000");
        contacts.add("ivanov@gmail.com");

        return Client.builder()
                .uuid(UUID.randomUUID())
                .fullName("Иван Иванов")
                .registrationDate(LocalDate.now())
                .contact(contacts)
                .build();
    }

    public List<Client> generateClients() {
        List<Client> clients = new ArrayList<>();
        //1 клиент
        Set<String> contactsPetrov = new HashSet<>();
        contactsPetrov.add("+3753292908877");
        contactsPetrov.add("petrov@gmail.com");
        contactsPetrov.add("tg @petrov");
        Client petrPetrov = Client.builder()
                .uuid(UUID.randomUUID())
                .fullName("Петр Петров")
                .registrationDate(LocalDate.now())
                .contact(contactsPetrov)
                .build();
        //2 клиент
        Set<String> contactsSmirnova = new HashSet<>();
        contactsSmirnova.add("+3753336005050");
        contactsSmirnova.add("alisasm@gmail.com");
        Client alisaSmirnova = Client.builder()
                .uuid(UUID.randomUUID())
                .fullName("Алиса Смирнова")
                .registrationDate(LocalDate.now())
                .contact(contactsSmirnova)
                .build();

        clients.add(petrPetrov);
        clients.add(alisaSmirnova);

        return clients;

    }

}
