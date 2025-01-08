package ru.clevertec.repository.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.Client;
import ru.clevertec.exception.CustomException;
import ru.clevertec.repository.api.CrudRepository;
import ru.clevertec.util.Constant;
import ru.clevertec.util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class ClientRepositoryImpl implements CrudRepository<UUID, Client>, ru.clevertec.repository.api.ClientRepository {

    private static final ClientRepositoryImpl INSTANCE = new ClientRepositoryImpl();


    @Override
    public Client create(Client client) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.persist(client);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_FAILED_TO_SAVE);
            }
            return client;
        }
    }

    @Override
    public Client update(Client client) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.merge(client);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_FAILED_TO_SAVE);
            }
            return client;
        }
    }

    @Override
    public Client findById(UUID uuid) {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
                JpaCriteriaQuery<Client> criteriaQuery = builder.createQuery(Client.class);

                JpaRoot<Client> clientJpaRoot = criteriaQuery.from(Client.class);
                criteriaQuery.select(criteriaQuery.from(Client.class))
                        .where(builder.equal(clientJpaRoot.get(Constant.FIELD_UUID), uuid));

                Query<Client> query = session.createQuery(criteriaQuery);

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
        Client client;
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                client = session.find(Client.class, uuid);
                session.remove(client);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_NOT_FOUND);
            }
        }
    }

    @Override
    public List<Client> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
                JpaCriteriaQuery<Client> cr = cb.createQuery(Client.class);
                cr.from(Client.class);
                Query<Client> query = session.createQuery(cr);
                transaction.commit();
                return query.getResultList();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_NOT_FOUND);
            }
        }
    }

    @Override
    public void addCarToClient(Client client, Car car) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                List<Car> cars = List.of(car);
                client.setCars(cars);
                session.merge(client);
                session.merge(car);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_FAILED_TO_SAVE);
            }
        }
    }

    @Override
    public List<Car> findCarsByClientUUID(UUID clientUUID) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                var query = session.createQuery
                        ("select car from Car car join car.clients cc where cc.uuid = :client_uuid", Car.class);
                query.setParameter(Constant.PARAMETER_CLIENT_UUID, clientUUID);
                return query.getResultList();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_NOT_FOUND_CLIENTS_CAR);
            }
        }
    }

    public static ClientRepositoryImpl getInstance() {
        return ClientRepositoryImpl.INSTANCE;
    }
}
