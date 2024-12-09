package ru.clevertec.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.repository.api.ICRUDRepository;
import ru.clevertec.repository.api.IReviewRepository;
import ru.clevertec.util.HibernateUtil;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class ReviewRepository implements ICRUDRepository<UUID, Review>, IReviewRepository {

    private static final ReviewRepository INSTANCE = new ReviewRepository();

    @Override
    public Review create(Review review) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.persist(review);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return review;
        }
    }

    @Override
    public Review update(Review review) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.merge(review);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return review;
        }
    }

    @Override
    public Review findById(UUID uuid) {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
                JpaCriteriaQuery<Review> criteriaQuery = builder.createQuery(Review.class);

                JpaRoot<Review> reviewJpaRoot = criteriaQuery.from(Review.class);
                criteriaQuery.select(criteriaQuery.from(Review.class))
                        .where(builder.equal(reviewJpaRoot.get("uuid"), uuid));

                Query<Review> query = session.createQuery(criteriaQuery);

                query.setMaxResults(1).uniqueResult();
                transaction.commit();
                return query.getSingleResult();
            } catch (Exception e) {
                transaction.rollback();
                System.out.println(e.getMessage());
                //throw e;
            }
            return null;
        }
    }

    @Override
    public void delete(UUID uuid) {
        Review review;
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                review = session.find(Review.class, uuid);
                session.remove(review);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }

        }
    }

    @Override
    public List<Review> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
                JpaCriteriaQuery<Review> cr = cb.createQuery(Review.class);
                cr.from(Review.class);
                Query<Review> query = session.createQuery(cr);
                transaction.commit();
                return query.getResultList();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public void addReview(Client client, Car car, String text, int rating) {

        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                var review = Review.builder()
                        .uuid(UUID.randomUUID())
                        .car(car)
                        .client(client)
                        .review(text)
                        .rating(rating)
                        .build();

                session.persist(review);
                transaction.commit();

            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Review> searchReviews(String keyword) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {

                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
                Root<Review> root = criteriaQuery.from(Review.class);

                String pattern = "%" + keyword + "%";

                criteriaQuery.select(criteriaQuery.from(Review.class)).where(criteriaBuilder.like(root.get("review"), pattern));

                transaction.commit();
                return session.createQuery(criteriaQuery).getResultList();


            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }


    public static ReviewRepository getInstance() {
        return ReviewRepository.INSTANCE;
    }


}

