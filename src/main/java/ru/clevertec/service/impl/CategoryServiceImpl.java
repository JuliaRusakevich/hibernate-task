package ru.clevertec.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.entity.Category;
import ru.clevertec.exception.CustomException;
import ru.clevertec.repository.impl.CategoryRepositoryImpl;
import ru.clevertec.service.api.BaseService;

import java.util.List;
import java.util.UUID;

@Slf4j
public class CategoryServiceImpl implements BaseService<UUID, Category> {

    private static final CategoryServiceImpl INSTANCE = new CategoryServiceImpl();
    final CategoryRepositoryImpl repository = CategoryRepositoryImpl.getInstance();

    @Override
    public Category add(Category category) {
        try {
            this.repository.create(category);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return category;
    }

    @Override
    public Category update(Category category) {
        try {
            this.repository.update(category);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return category;
    }

    @Override
    public Category findById(UUID uuid) {
        Category category = new Category();
        try {
            category = this.repository.findById(uuid);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return category;
    }

    @Override
    public void delete(UUID uuid) {
        try {
            this.repository.delete(uuid);
        } catch (CustomException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Category> findAll() {
        return null;
    }

    public static CategoryServiceImpl getInstance() {
        return CategoryServiceImpl.INSTANCE;
    }
}
