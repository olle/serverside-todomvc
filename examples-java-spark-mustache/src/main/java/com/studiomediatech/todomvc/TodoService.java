package com.studiomediatech.todomvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;


public final class TodoService {

    private final Map<String, Todo> todos = new ConcurrentHashMap<>();
    private final AtomicBoolean hidden = new AtomicBoolean(false);

    public void addNewTodo(String text) {

        Todo todo = Todo.valueOf(text);
        todos.put(todo.getId(), todo);
    }


    public List<Todo> getActiveTodos() {

        return todos.values().stream().filter(Todo::isActive).toList();
    }


    public List<Todo> getCompletedTodos() {

        return todos.values().stream().filter(Todo::isCompleted).toList();
    }


    public void editTodo(String id) {

        updateTodoById(id, Todo::markAsBeingEdited);
    }


    public void updateTodo(String id, String update) {

        updateTodoById(id, todo -> todo.updateTodo(update));
    }


    public void completeTodoItem(String id) {

        updateTodoById(id, Todo::markCompleted);
    }


    public void activateTodoItem(String id) {

        updateTodoById(id, Todo::markActive);
    }


    private void updateTodoById(String id, UnaryOperator<Todo> operation) {

        Optional.ofNullable(todos.get(id))
            .map(operation)
            .ifPresent(updated -> todos.put(updated.getId(), updated));
    }


    public void deleteTodoItem(String id) {

        todos.remove(id);
    }


    public void hideCompleted(String toggle) {

        this.hidden.set(true);
    }


    public void showCompleted(String toggle) {

        this.hidden.set(false);
    }


    public void clearCompleted(String clear) {

        todos.entrySet().removeIf(e -> e.getValue().isCompleted());
    }


    public boolean isHidden() {

        return hidden.get();
    }


    public boolean isEditing() {

        return todos.values().stream().anyMatch(Todo::isEditing);
    }
}
