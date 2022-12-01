package com.studiomediatech.todomvc;

import org.springframework.stereotype.Repository;

import java.util.Map;
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
}
