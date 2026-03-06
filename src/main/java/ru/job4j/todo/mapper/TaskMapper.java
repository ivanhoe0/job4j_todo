package ru.job4j.todo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.view.TaskView;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Mapping(target = "priority", source = "priorityId")
    @Mapping(target = "categories", source = "categoryIds")
    public abstract Task getEntityFromView(TaskView view);

    public Priority map(Integer priorityId) {
        return priorityRepository.findById(priorityId).orElse(null);
    }

    public List<Category> map(List<Integer> categoryIds) {
        return categoryIds.stream().map(categoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
