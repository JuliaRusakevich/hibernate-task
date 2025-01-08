package ru.clevertec;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.exception.CustomException;
import ru.clevertec.repository.api.ClientRepository;
import ru.clevertec.repository.impl.ClientRepositoryImpl;
import ru.clevertec.service.impl.CarServiceImpl;
import ru.clevertec.service.impl.CarShowroomServiceImpl;
import ru.clevertec.service.impl.CategoryServiceImpl;
import ru.clevertec.service.impl.ClientServiceImpl;
import ru.clevertec.service.impl.ReviewServiceImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

        //HibernateUtil.dropAllTables();

        CarServiceImpl carService = CarServiceImpl.getInstance();
        CategoryServiceImpl categoryService = CategoryServiceImpl.getInstance();
        CarShowroomServiceImpl carShowroomService = CarShowroomServiceImpl.getInstance();
        ClientServiceImpl clientService = ClientServiceImpl.getInstance();
        ReviewServiceImpl reviewService = ReviewServiceImpl.getInstance();


        /**Добавление автомобиля: addCar(Car car)
         for (Car generateCar : EntityFactory.generateCars()) {
         carService.add(generateCar);
         }
         */

        /**Добавление автосалонов
         for (CarShowroom carShowroom : EntityFactory.generateShowrooms()) {
         carShowroomService.add(carShowroom);
         }
         */

        /**Добавление категорий
         for (Category category : EntityFactory.generateCategories()) {
         categoryService.add(category);
         }
         */

        /**Регистрация клиента: addClient(Client client)
         for (Client client : EntityFactory.generateClients()) {
         clientService.add(client);
         }
         */

        /**Обновить авто (передавать все параметры!)
         Car car = Car.builder()
         .uuid(UUID.fromString("97c12367-58ed-4e98-84ac-a326e7cfa6c7"))
         .model("M4 Competition M xDrive Coup")
         .make("BMW")
         .year(2023)
         .price(BigDecimal.valueOf(60000))
         .category(Category.builder()
         .uuid(UUID.fromString("93a87b3f-7964-4ad6-8f08-91e8fa75ae39"))
         .build())
         .build();
         carService.update(car);
         */

        /** Привязка автомобиля к автосалону
         Car carById = carService.findById(UUID.fromString("38e07d72-6401-4847-9292-c10c4a0c383d"));
         CarShowroom showroomById = carShowroomService.findById(UUID.fromString("93a87b3f-7964-4ad6-8f08-91e8fa75ae39"));
         carShowroomService.assignCarToShowroom(carById, showroomById);
         */

        /** Привязка автомобиля к клиенту: buyCar(Client client, Car car)
         Car carById = carService.findById(UUID.fromString("ebc8d060-7bad-48f4-b6f9-0b27449bbace"));
         Client clientById = clientService.findById(UUID.fromString("51849f93-5c10-45bb-ab85-028943384078"));
         clientService.buyCar(clientById, carById);
         */

        /** Поиск авто по uuid
         Car byId = carService.findById(UUID.fromString("73fc1f22-2a85-462c-a6ed-2c2d219546a3"));
         System.out.println(byId.getModel());
         */

        /**Поиск автомобилей по фильтрам: findCarsByFilters(String brand, String category, int year, double minPrice, double maxPrice). +
         */

        /** Добавление отзыва клиента: addReview(Client client, Car car, String text, int rating).
         try {
         Client client = clientService.findById(UUID.fromString("867b2880-08fa-444b-8f70-a43236d25d1c"));
         Car car = carService.findById(UUID.fromString("0eb60d21-ee27-49a2-8715-d24dc40bfad2"));
         String textReview = "WOW";
         int rating = 5;
         reviewService.addReview(client, car, textReview, rating);
         }catch (Exception e){
         System.out.println(e.getMessage());
         } */


        /**Полнотекстовый поиск отзывов: searchReviews(String keyword).
         List<Review> reviews = reviewService.searchReviews("WO");
         for (Review r : reviews) {
         System.out.println(r.getReview() + " " + r.getCar().getMake());
         }
         */
    }
}
