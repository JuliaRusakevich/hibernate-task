package ru.clevertec.repository.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.repository.api.ICRUDRepository;
import ru.clevertec.repository.api.ICarShowroomRepository;
import ru.clevertec.util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class CarShowroomRepository implements ICRUDRepository<UUID, CarShowroom>, ICarShowroomRepository {

    private static final CarShowroomRepository INSTANCE = new CarShowroomRepository();

    @Override
    public CarShowroom create(CarShowroom showroom) {
        try (var session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(showroom);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return showroom;
        }
    }

    @Override
    public CarShowroom update(CarShowroom showroom) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.merge(showroom);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return showroom;
        }
    }

    @Override
    public CarShowroom findById(UUID uuid) {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
                JpaCriteriaQuery<CarShowroom> criteriaQuery = builder.createQuery(CarShowroom.class);

                JpaRoot<CarShowroom> carShowroomJpaRoot = criteriaQuery.from(CarShowroom.class);
                criteriaQuery.select(criteriaQuery.from(CarShowroom.class))
                        .where(builder.equal(carShowroomJpaRoot.get("uuid"), uuid));

                Query<CarShowroom> query = session.createQuery(criteriaQuery);

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
        CarShowroom carShowroom;
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                carShowroom = session.find(CarShowroom.class, uuid);
                session.remove(carShowroom);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<CarShowroom> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                JpaCriteriaQuery<CarShowroom> criteriaQuery = criteriaBuilder.createQuery(CarShowroom.class);
                criteriaQuery.from(CarShowroom.class);
                Query<CarShowroom> query = session.createQuery(criteriaQuery);
                transaction.commit();
                return query.getResultList();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public void addCarToShowroom(Car car, CarShowroom showroom) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            car.setCarShowroom(showroom);
            try {
                session.merge(car);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    public static CarShowroomRepository getInstance() {
        return CarShowroomRepository.INSTANCE;
    }
}
