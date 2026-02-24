package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();

    boolean delete(Integer id);

    Task addTask(Task task);

    Optional<Task> findById(int id);

    List<Task> findFinishedTasks();

    List<Task> findUnFinishedTasks();

    boolean makeDone(Integer id);

    boolean update(Task task);
}
