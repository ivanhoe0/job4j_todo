package ru.job4j.todo.repository.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPriorityRepository implements PriorityRepository {

    private final CrudRepository repository;

    @Override
    public Optional<Priority> findById(int id) {
        return repository.optional("FROM Priority WHERE id = :pid", Priority.class, Map.of("pid", id));
    }

    @Override
    public List<Priority> findAll() {
        return repository.query("FROM Priority", Priority.class);
    }
}
