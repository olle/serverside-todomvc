package com.studiomediatech.todomvc;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class TodoMvcRepository {

    private final Map<UUID, Todo> todos = new ConcurrentHashMap<>();

    public long activeTodoCount() {

        return todos.values().stream().filter(Todo::isActive).count();
    }


    public void save(Todo todo) {

        todos.put(todo.getUUID(), todo);
    }


    public Collection<Todo> findAllActiveTodos() {

        return todos.values().stream().filter(Todo::isActive).toList();
    }


    public Optional<Todo> findByUUID(UUID uuid) {

        return Optional.ofNullable(todos.get(uuid));
    }


    public Collection<Todo> findAllCompletedTodos() {

        return todos.values().stream().filter(Todo::isCompleted).toList();
    }
}
