package ru.job4j.todo.repository.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmCategoryRepository implements CategoryRepository {

    private final CrudRepository repository;

    @Override
    public Optional<Category> findById(int id) {
        return repository.optional("FROM Category WHERE id = :cid", Category.class, Map.of("cid", id));
    }

    @Override
    public List<Category> findAll() {
        return repository.query("FROM Category", Category.class);
    }
}
