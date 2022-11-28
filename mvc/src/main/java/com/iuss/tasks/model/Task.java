package com.iuss.tasks.model;

import jakarta.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private boolean done;

    @ManyToOne
    private TaskList list;

    public Task() {
    }

    public Task(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public TaskList getList() {
        return list;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String title) {
        this.name = title;
    }

    public void setList(TaskList list) {
        this.list = list;
    }
}
