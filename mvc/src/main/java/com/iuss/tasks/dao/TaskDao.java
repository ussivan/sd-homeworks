package com.iuss.tasks.dao;

import com.iuss.tasks.model.Task;
import com.iuss.tasks.model.TaskList;
import com.iuss.tasks.repository.TaskListRep;
import com.iuss.tasks.repository.TaskRep;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDao {

    private final TaskRep taskRep;
    private final TaskListRep taskListRep;

    public TaskDao(TaskRep repository, TaskListRep taskListRep) {
        this.taskRep = repository;
        this.taskListRep = taskListRep;
    }

    public void addTask(Task task) {
        task.setId(0);
        taskRep.save(task);
    }

    public void addTaskList(TaskList taskList) {
        taskList.setId(0);
        taskListRep.save(taskList);
    }

    public List<TaskList> getTaskLists() {
        return taskListRep.findAll();
    }

    public void deleteTaskList(long id) {
        taskListRep.deleteById(id);
    }

    public void toggleTask(long id) {
        Task task = taskRep.getReferenceById(id);
        task.setDone(!task.getDone());
        taskRep.save(task);
    }
}
