package ru.clevertec.service.impl;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.repository.impl.ClientRepository;
import ru.clevertec.repository.impl.ReviewRepository;
import ru.clevertec.service.api.IReviewService;

import java.util.List;
import java.util.UUID;

public class ReviewService implements IReviewService {

    private static final ReviewService INSTANCE = new ReviewService();
    final ReviewRepository reviewRepository = ReviewRepository.getInstance();
    final ClientRepository clientRepository = ClientRepository.getInstance();

    @Override
    public void addReview(Client client, Car car, String text, int rating) {
        UUID carByClientUUID = getCarByClientUUID(client);
        if (carByClientUUID.equals(car.getUuid())) {
            this.reviewRepository.addReview(client, car, text, rating);
        } else {
            System.out.println("The client did not buy car");
        }

    }

    @Override
    public List<Review> searchReviews(String keyword) {
        return this.reviewRepository.searchReviews(keyword);
    }

    public static ReviewService getInstance() {
        return ReviewService.INSTANCE;
    }

    private UUID getCarByClientUUID(Client client) {
        var carsFromDB = this.clientRepository.findCarsByClientUUID(client.getUuid());
        return carsFromDB.stream()
                .map(Car::getUuid)
                .peek(System.out::println)
                .findFirst().orElseThrow();

    }
}
