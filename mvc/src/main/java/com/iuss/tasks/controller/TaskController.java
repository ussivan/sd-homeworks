package com.iuss.tasks.controller;

import com.iuss.tasks.model.Task;
import com.iuss.tasks.dao.TaskDao;
import com.iuss.tasks.model.TaskList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {

    private final TaskDao taskDao;

    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @GetMapping("/tasks")
    public String getTasks(ModelMap map) {
        initModel(map, taskDao.getTaskLists());
        return "index";
    }

    @PostMapping("/add-task")
    public String addTask(@ModelAttribute("task") Task task) {
        taskDao.addTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/add-task-list")
    public String addTaskList(@ModelAttribute("taskList") TaskList taskList) {
        taskDao.addTaskList(taskList);
        return "redirect:/tasks";
    }

    @GetMapping("/delete-task-list")
    public String deletetaskList(@RequestParam("id") long id) {
        taskDao.deleteTaskList(id);
        return "redirect:/tasks";
    }

    @GetMapping("/toggle-task")
    public String toggleTask(@RequestParam("id") long id) {
        taskDao.toggleTask(id);
        return "redirect:/tasks";
    }

    private void initModel(ModelMap map, List<TaskList> tasks) {
        map.addAttribute("taskLists", tasks);
        map.addAttribute("task", new Task());
        map.addAttribute("taskList", new TaskList());
    }
}
