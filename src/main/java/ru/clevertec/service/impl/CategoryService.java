package ru.clevertec.service.impl;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.Category;
import ru.clevertec.repository.impl.CategoryRepository;
import ru.clevertec.service.api.ICrudService;

import java.util.List;
import java.util.UUID;

public class CategoryService implements ICrudService<UUID, Category> {

    private static final CategoryService INSTANCE = new CategoryService();
    final CategoryRepository repository = CategoryRepository.getInstance();

    @Override
    public Category add(Category category) {
        try {
            this.repository.create(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return category;
    }

    @Override
    public Category update(Category category) {
        try {
            this.repository.update(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return category;
    }

    @Override
    public Category findById(UUID uuid) {
        Category category = new Category();
        try {
            category = this.repository.findById(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return category;
    }

    @Override
    public void delete(UUID uuid) {
        this.repository.delete(uuid);

    }

    @Override
    public List<Category> findAll() {
        return null;
    }

    public static CategoryService getInstance() {
        return CategoryService.INSTANCE;
    }
}
