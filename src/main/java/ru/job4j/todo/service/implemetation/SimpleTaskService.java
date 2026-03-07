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
import java.util.TimeZone;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    @Override
    public List<TaskView> findUnFinishedTasks(User user) {
        return taskRepository.findUnFinishedTasks()
                .stream()
                .map(mapper::getViewFromEntity)
                .peek(e -> {
                    if (user.getTimeZone() != null) {
                        e.setCreated(e.getCreated().withZoneSameInstant(TimeZone.getTimeZone(user.getTimeZone()).toZoneId()));
                    }
                })
                .toList();
    }

    @Override
    public List<TaskView> findFinishedTasks(User user) {
        return taskRepository.findFinishedTasks()
                .stream()
                .map(mapper::getViewFromEntity)
                .peek(e -> {
                    if (user.getTimeZone() != null) {
                        e.setCreated(e.getCreated().withZoneSameInstant(TimeZone.getTimeZone(user.getTimeZone()).toZoneId()));
                    }
                })
                .toList();
    }

    @Override
    public Optional<TaskView> findById(int id) {
        return taskRepository.findById(id).map(mapper::getViewFromEntity);
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
    public List<TaskView> findAll(User user) {
        return taskRepository.findAll()
                .stream()
                .map(mapper::getViewFromEntity)
                .peek(e -> {
                    if (user.getTimeZone() != null) {
                        e.setCreated(e.getCreated().withZoneSameInstant(TimeZone.getTimeZone(user.getTimeZone()).toZoneId()));
                    }
                })
                .toList();
    }

    @Override
    public boolean makeDone(Integer id) {
        return taskRepository.makeDone(id);
    }

    @Override
    public boolean update(TaskView view) {
        return taskRepository.update(mapper.getEntityFromView(view));
    }
}
