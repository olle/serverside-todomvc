package com.studiomediatech.todomvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class TodoService {

    private static final Map<String, Todo> todos = new ConcurrentHashMap<>();

    public void addNewTodo(String todo) {

        Todo t = new Todo(todo);
        todos.put(t.getId(), t);
    }


    public List<Todo> getActiveTodos() {

        return todos.values().stream().filter(Todo::isActive).toList();
    }


    public List<Todo> getCompletedTodos() {

        return todos.values().stream().filter(Todo::isCompleted).toList();
    }


    public void completeTodoItem(String id) {

        Optional.ofNullable(todos.get(id)).ifPresent(Todo::markCompleted);
    }
}
