package ru.clevertec.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.exception.CustomException;
import ru.clevertec.repository.impl.ReviewRepositoryImpl;
import ru.clevertec.service.api.ReviewService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private static final ReviewServiceImpl INSTANCE = new ReviewServiceImpl();
    final ReviewRepositoryImpl reviewRepository = ReviewRepositoryImpl.getInstance();
    final ClientServiceImpl clientService = ClientServiceImpl.getInstance();

    @Override
    public void addReview(Client client, Car car, String text, int rating) {
        try {
            checkIfClientBoughtCar(client);
            this.reviewRepository.addReview(client, car, text, rating);
        } catch (CustomException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Review> searchReviews(String keyword) {
        List<Review> reviews = new ArrayList<>();
        try {
            reviews = this.reviewRepository.searchReviews(keyword);
        } catch (CustomException e) {
            log.error(e.getMessage());
        }
        return reviews;
    }

    public static ReviewServiceImpl getInstance() {
        return ReviewServiceImpl.INSTANCE;
    }

    private void checkIfClientBoughtCar(Client client) {
        var carsFromDB = this.clientService.findCarsByClientUuid(client.getUuid());
        carsFromDB.stream()
                .map(Car::getUuid)
                .peek(System.out::println) // удалить!
                .findFirst().orElseThrow();

    }
}
