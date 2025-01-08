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
import ru.clevertec.exception.CustomException;
import ru.clevertec.repository.api.CrudRepository;
import ru.clevertec.util.Constant;
import ru.clevertec.util.HibernateUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CarRepositoryImpl implements CrudRepository<UUID, Car>, ru.clevertec.repository.api.CarRepository {

    private static final CarRepositoryImpl INSTANCE = new CarRepositoryImpl();


    @Override
    public Car create(Car car) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.persist(car);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_FAILED_TO_SAVE);
            }
        }
        return car;
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
                throw new CustomException(Constant.ERROR_MESSAGE_FAILED_TO_SAVE);
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
                        .where(builder.equal(carJpaRoot.get(Constant.FIELD_UUID), uuid));

                Query<Car> query = session.createQuery(criteriaQuery);

                query.setMaxResults(Constant.MAX_RESULT_WHEN_FIND_BY_UUID).uniqueResult();
                transaction.commit();
                return query.getSingleResult();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_NOT_FOUND);
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
                throw new CustomException(Constant.ERROR_MESSAGE_NOT_FOUND);
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
                throw new CustomException(Constant.ERROR_MESSAGE_NOT_FOUND);
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
                    if (typeSorting.equalsIgnoreCase(Constant.SORT_ASCENDING)) {
                        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Constant.FIELD_PRICE)));
                    }
                    if (typeSorting.equalsIgnoreCase(Constant.SORT_DESCENDING)) {
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Constant.FIELD_PRICE)));
                    }
                }
                transaction.commit();
                return session.createQuery(criteriaQuery).getResultList();

            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_NOT_FOUND);
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
                throw new CustomException(Constant.ERROR_MESSAGE_NOT_FOUND);
            }
        }
    }

    public static CarRepositoryImpl getInstance() {
        return CarRepositoryImpl.INSTANCE;
    }

    private Predicate getPredicate(String make, String category, Integer year, BigDecimal minPrice, BigDecimal maxPrice, CriteriaBuilder criteriaBuilder, Root<Car> root) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (make != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(Constant.FIELD_MAKE), make));
        }
        if (year != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(Constant.FIELD_YEAR), year));
        }
        if (category != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(Constant.FIELD_CATEGORY).get(Constant.FIELD_CATEGORY_NAME), category));
        }
        if (minPrice != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.ge(root.get(Constant.FIELD_PRICE), minPrice));
        }
        if (maxPrice != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.le(root.get(Constant.FIELD_PRICE), maxPrice));
        }
        return predicate;
    }
}
