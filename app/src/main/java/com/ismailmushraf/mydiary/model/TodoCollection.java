package com.ismailmushraf.mydiary.model;

import java.util.ArrayList;
import java.util.List;

public class TodoCollection {

    private String title;
    private long createdAt;
    private long updatedAt;
    private final List<Todo> todos;

    public TodoCollection(String title, long createdAt) {
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = 0;
        todos = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void addTodo(Todo todo) {
        todos.add(todo);
    }

    public void removeTodo(Todo todo) {
        todos.remove(todo);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
