package com.studiomediatech.todomvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public final class TodoService {

    private static final Map<String, Todo> todos = new ConcurrentHashMap<>();

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


    private void save(Todo todo) {

        todos.put(todo.getId(), todo);
    }
}
