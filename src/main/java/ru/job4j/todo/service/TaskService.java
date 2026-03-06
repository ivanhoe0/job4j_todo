package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.view.TaskView;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();

    boolean delete(int id);

    TaskView addTask(TaskView view, User user);

    Optional<Task> findById(int id);

    List<Task> findFinishedTasks();

    List<Task> findUnFinishedTasks();

    boolean makeDone(Integer id);

    boolean update(Task task);
}
