package ru.job4j.todo.service.implemetation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.mapper.TaskMapper;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.TaskRepository;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.view.TaskView;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    @Override
    public List<Task> findUnFinishedTasks() {
        return taskRepository.findUnFinishedTasks();
    }

    @Override
    public List<Task> findFinishedTasks() {
        return taskRepository.findFinishedTasks();
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public TaskView addTask(TaskView view, User user) {
        Task task = mapper.getEntityFromView(view);
        taskRepository.addTask(task, user);
        return view;
    }

    @Override
    public boolean delete(int id) {
        return taskRepository.delete(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public boolean makeDone(Integer id) {
        return taskRepository.makeDone(id);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }
}
