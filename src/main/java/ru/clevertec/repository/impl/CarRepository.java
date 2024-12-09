package ru.clevertec.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import ru.clevertec.entity.Car;
import ru.clevertec.repository.api.ICRUDRepository;
import ru.clevertec.repository.api.ICarRepository;
import ru.clevertec.util.HibernateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarRepository implements ICRUDRepository<UUID, Car>, ICarRepository {

    private static final CarRepository INSTANCE = new CarRepository();

    @Override
    public Car create(Car car) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.persist(car);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return car;
        }
    }

    @Override
    public Car update(Car car) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.merge(car);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return car;
        }
    }

    @Override
    public Car findById(UUID uuid) {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
                JpaCriteriaQuery<Car> criteriaQuery = builder.createQuery(Car.class);

                JpaRoot<Car> carJpaRoot = criteriaQuery.from(Car.class);
                criteriaQuery.select(criteriaQuery.from(Car.class))
                        .where(builder.equal(carJpaRoot.get("uuid"), uuid));

                Query<Car> query = session.createQuery(criteriaQuery);

                query.setMaxResults(1).uniqueResult();
                transaction.commit();
                return query.getSingleResult();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public void delete(UUID uuid) {
        Car car;
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                car = session.find(Car.class, uuid);
                session.remove(car);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<Car> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
                JpaCriteriaQuery<Car> cr = cb.createQuery(Car.class);
                cr.from(Car.class);
                Query<Car> query = session.createQuery(cr);
                transaction.commit();
                return query.getResultList();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<Car> findCarsSortedByPrice(String typeSorting) {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                JpaCriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
                JpaRoot<Car> root = criteriaQuery.from(Car.class);
                if (typeSorting != null) {
                    if (typeSorting.equalsIgnoreCase("ASC")) {
                        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")));
                    }
                    if (typeSorting.equalsIgnoreCase("DESC")) {
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")));
                    }
                }
                transaction.commit();
                return session.createQuery(criteriaQuery).getResultList();

            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<Car> findCarsByFilters(String make, String category, Integer year, BigDecimal minPrice, BigDecimal maxPrice) {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
                Root<Car> root = criteriaQuery.from(Car.class);

                Predicate predicate = getPredicate(make, category, year, minPrice, maxPrice, criteriaBuilder, root);
                criteriaQuery.where(predicate);
                transaction.commit();
                return session.createQuery(criteriaQuery).getResultList();

            } catch (Exception e) {
                transaction.rollback();
                throw e;

            }
        }
    }


    public static CarRepository getInstance() {
        return CarRepository.INSTANCE;
    }

    private Predicate getPredicate(String make, String category, Integer year, BigDecimal minPrice, BigDecimal maxPrice, CriteriaBuilder criteriaBuilder, Root<Car> root) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (make != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("make"), make));
        }
        if (year != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("year"), year));
        }
        if (category != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category").get("name"), category));
        }
        if (minPrice != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.ge(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.le(root.get("price"), maxPrice));
        }
        return predicate;
    }
}
