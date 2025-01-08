package ru.clevertec.repository.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import ru.clevertec.entity.Category;
import ru.clevertec.exception.CustomException;
import ru.clevertec.repository.api.CrudRepository;
import ru.clevertec.util.Constant;
import ru.clevertec.util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class CategoryRepositoryImpl implements CrudRepository<UUID, Category> {

    private static final CategoryRepositoryImpl INSTANCE = new CategoryRepositoryImpl();

    @Override
    public Category create(Category category) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.persist(category);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_FAILED_TO_SAVE);
            }
            return category;
        }
    }

    @Override
    public Category update(Category category) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.merge(category);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_FAILED_TO_SAVE);
            }
            return category;
        }
    }

    @Override
    public Category findById(UUID uuid) {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
                JpaCriteriaQuery<Category> criteriaQuery = builder.createQuery(Category.class);

                JpaRoot<Category> categoryJpaRoot = criteriaQuery.from(Category.class);
                criteriaQuery.select(criteriaQuery.from(Category.class))
                        .where(builder.equal(categoryJpaRoot.get(Constant.FIELD_UUID), uuid));

                Query<Category> query = session.createQuery(criteriaQuery);

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
        Category category;
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                category = session.find(Category.class, uuid);
                session.remove(category);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_NOT_FOUND);
            }
        }
    }

    @Override
    public List<Category> findAll() {
        try (Session session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
                JpaCriteriaQuery<Category> cr = cb.createQuery(Category.class);
                cr.from(Category.class);
                Query<Category> query = session.createQuery(cr);
                transaction.commit();
                return query.getResultList();
            } catch (Exception e) {
                transaction.rollback();
                throw new CustomException(Constant.ERROR_MESSAGE_NOT_FOUND);
            }
        }
    }

    public static CategoryRepositoryImpl getInstance() {
        return CategoryRepositoryImpl.INSTANCE;
    }
}
