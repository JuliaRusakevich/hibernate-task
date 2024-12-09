package ru.clevertec.repository.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import ru.clevertec.entity.Category;
import ru.clevertec.repository.api.ICRUDRepository;
import ru.clevertec.util.HibernateUtil;

import java.util.List;
import java.util.UUID;

public class CategoryRepository implements ICRUDRepository<UUID, Category> {

    private static final CategoryRepository INSTANCE = new CategoryRepository();

    @Override
    public Category create(Category category) {
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                session.persist(category);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
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
                throw e;
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
                        .where(builder.equal(categoryJpaRoot.get("uuid"), uuid));

                Query<Category> query = session.createQuery(criteriaQuery);

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
        Category category;
        try (var session = HibernateUtil.getSession()) {
            var transaction = session.beginTransaction();
            try {
                category = session.find(Category.class, uuid);
                session.remove(category);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
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
                throw e;
            }
        }
    }

    public static CategoryRepository getInstance() {
        return CategoryRepository.INSTANCE;
    }
}
