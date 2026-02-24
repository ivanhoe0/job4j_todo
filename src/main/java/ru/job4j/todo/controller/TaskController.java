package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@Controller
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/all";
    }

    @GetMapping("tasks/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("tasks/create")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            taskService.addTask(task);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("tasks/finished")
    public String getFinishedTasks(Model model) {
        model.addAttribute("tasks", taskService.findFinishedTasks());
        return "tasks/finished";
    }

    @GetMapping("tasks/new")
    public String getNewTasks(Model model) {
        model.addAttribute("tasks", taskService.findUnFinishedTasks());
        return "tasks/new";
    }

    @GetMapping("tasks/{id}")
    public String findById(@PathVariable int id, Model model) {
        var optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("task", optionalTask.get());
        return "tasks/onetask";
    }

    @GetMapping("tasks/update/{id}")
    public String makeDone(Model model, @PathVariable int id) {
        if (taskService.makeDone(id)) {
            return "redirect:/";
        } else {
            model.addAttribute("message", "Произошла ошибка обновления");
            return "errors/404";
        }
    }

    @GetMapping("tasks/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        var optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("task", optionalTask.get());
        return "tasks/one";
    }

    @PostMapping("tasks/update")
    public String update(@ModelAttribute Task task, Model model) {
        if (taskService.update(task)) {
            return "redirect:/";
        } else {
            model.addAttribute("message", "Произошла ошибка обновления");
            return "errors/404";
        }
    }

    @GetMapping("tasks/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        if (taskService.delete(id)) {
            return "redirect:/";
        } else {
            model.addAttribute("message", "Произошла ошибка удаления");
            return "errors/404";
        }
    }
}
