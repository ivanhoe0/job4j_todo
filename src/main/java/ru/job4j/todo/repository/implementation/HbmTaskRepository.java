package ru.job4j.todo.repository.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmTaskRepository implements TaskRepository {

    private final CrudRepository crudRepository;

    @Override
    public List<Task> findUnFinishedTasks() {
        return crudRepository.query("FROM Task t WHERE t.done = FALSE", Task.class);
    }

    @Override
    public List<Task> findFinishedTasks() {
        return crudRepository.query("FROM Task t WHERE t.done = TRUE", Task.class);
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional("FROM Task t WHERE t.id = :fid", Task.class, Map.of("fid", id));
    }

    @Override
    public Task addTask(Task task) {
        task.setCreated(LocalDateTime.now());
        crudRepository.run(session -> session.save(task));
        return task;
    }

    @Override
    public boolean delete(Integer id) {
        return crudRepository.run("DELETE Task t WHERE t.id = :tid", Map.of("tid", id)) > 0;
    }

    @Override
    public List<Task> findAll() {
        return crudRepository.query("FROM Task", Task.class);
    }

    @Override
    public boolean makeDone(Integer id) {
        return crudRepository.run("UPDATE Task  SET done = TRUE WHERE id = :fid", Map.of("fid", id)) > 0;
    }

    @Override
    public boolean update(Task task) {
        var map = new HashMap<String, Object>();
        map.put("fTitle", task.getTitle());
        map.put("fDesc", task.getDescription());
        map.put("fDone", task.isDone());
        map.put("fid", task.getId());
        return crudRepository.run("UPDATE Task t SET t.title = :fTitle, t.description = :fDesc, t.done = :fDone WHERE t.id = :fid", map) > 0;
    }
}
