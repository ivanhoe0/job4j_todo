package ru.job4j.todo.view;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TaskView {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime created;
    private boolean done;
    private Integer priorityId;
    private List<Integer> categoryIds = new ArrayList<>();
}
