package ru.job4j.todo.view;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TaskView {
    private Integer id;
    private String title;
    private String description;
    private ZonedDateTime created;
    private boolean done;
    private int userId;
    private String user;
    private Integer priorityId;
    private String priority;
    private List<Integer> categoryIds = new ArrayList<>();
}
