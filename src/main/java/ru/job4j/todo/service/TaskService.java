package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.view.TaskView;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskView> findAll(User user);

    boolean delete(int id);

    TaskView addTask(TaskView view, User user);

    Optional<TaskView> findById(int id);

    List<TaskView> findFinishedTasks(User user);

    List<TaskView> findUnFinishedTasks(User user);

    boolean makeDone(Integer id);

    boolean update(TaskView view);
}
