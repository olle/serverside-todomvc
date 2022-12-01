package com.studiomediatech.todomvc;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class TodoMvcService {

    private final TodoMvcRepository repo;

    public TodoMvcService(TodoMvcRepository repo) {

        this.repo = repo;
    }

    public Map<String, ?> fetchAttributesForIndexPage() {

        Map<String, Object> map = new HashMap<>();

        map.put("activeCount", repo.activeTodoCount());
        map.put("active", repo.findAllActiveTodos());
        map.put("completed", repo.findAllCompletedTodos());

        return map;
    }


    public void createNewTodo(String todo) {

        repo.save(Todo.from(todo));
    }


    public void markTodoAsCompleted(String uuid) {

        repo.findByUUID(UUID.fromString(uuid)).map(Todo::markAsCompleted).ifPresent(repo::save);
    }


    public void deleteTodo(String uuid) {

        repo.deleteByUUID(UUID.fromString(uuid));
    }
}
