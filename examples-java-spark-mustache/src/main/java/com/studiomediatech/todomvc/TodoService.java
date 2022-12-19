package com.studiomediatech.todomvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public final class TodoService {

    private final Map<String, Todo> todos = new ConcurrentHashMap<>();
    private final AtomicBoolean hidden = new AtomicBoolean(false);

    public void addNewTodo(String text) {

        this.save(Todo.valueOf(text));
    }


    public List<Todo> getActiveTodos() {

        return todos.values().stream().filter(Todo::isActive).toList();
    }


    public List<Todo> getCompletedTodos() {

        return todos.values().stream().filter(Todo::isCompleted).toList();
    }


    public void completeTodoItem(String id) {

        Optional.ofNullable(todos.get(id)).map(Todo::markCompleted).ifPresent(this::save);
    }


    public void activateTodoItem(String id) {

        Optional.ofNullable(todos.get(id)).map(Todo::markActive).ifPresent(this::save);
    }


    public void deleteTodoItem(String id) {

        todos.remove(id);
    }


    private void save(Todo todo) {

        todos.put(todo.getId(), todo);
    }


    public void hideCompleted(String toggle) {

        this.hidden.set(true);
    }


    public void showCompleted(String toggle) {

        this.hidden.set(false);
    }


    public boolean isHidden() {

        return hidden.get();
    }
}
