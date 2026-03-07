package ru.job4j.todo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.repository.UserRepository;
import ru.job4j.todo.view.TaskView;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "priority", source = "priorityId")
    @Mapping(target = "categories", source = "categoryIds")
    @Mapping(target = "user", source = "userId")
    public abstract Task getEntityFromView(TaskView view);

    @Mapping(target = "priorityId", source = "priority.id")
    @Mapping(target = "categoryIds", source = "categories")
    @Mapping(target = "user", source = "user.name")
    @Mapping(target = "priority", source = "priority.name")
    public abstract TaskView getViewFromEntity(Task task);

    public Priority map(Integer priorityId) {
        return priorityRepository.findById(priorityId).orElse(null);
    }

    public User map(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public LocalDateTime map(ZonedDateTime created) {
        return created != null ? created.toLocalDateTime() : null;
    }

    public List<Category> map(List<Integer> categoryIds) {
        return categoryIds.stream().map(categoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public Integer mapCategoryToId(Category category) {
        return category != null ? category.getId() : null;
    }

    public ZonedDateTime map(LocalDateTime created) {
        return created.atZone(TimeZone.getDefault().toZoneId());
    }
}
