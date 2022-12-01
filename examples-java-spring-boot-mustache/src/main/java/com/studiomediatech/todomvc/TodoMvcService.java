package com.studiomediatech.todomvc;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@Service
public class TodoMvcService {

    private final TodoMvcRepository repo;

    public TodoMvcService(TodoMvcRepository repo) {

        this.repo = repo;
    }

    public Map<String, ?> fetchAttributesForIndexPage() {

        return Map.of("activeCount", repo.activeTodoCount(), "active", repo.findAllActiveTodos(), "completed",
                repo.findAllCompletedTodos());
    }


    public void createNewTodo(String todo) {

        repo.save(Todo.from(todo));
    }


    public void markTodoAsCompleted(String uuid) {

        repo.findByUUID(UUID.fromString(uuid)).map(Todo::markAsCompleted).ifPresent(repo::save);
    }
}
