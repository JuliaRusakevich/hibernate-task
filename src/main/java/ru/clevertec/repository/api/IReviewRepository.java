package ru.clevertec.repository.api;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;

import java.util.List;

public interface IReviewRepository {

    void addReview(Client client, Car car, String text, int rating);

    List<Review> searchReviews(String keyword);
}
