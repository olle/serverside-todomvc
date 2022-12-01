package com.studiomediatech.todomvc;

import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class TodoMvcService {

    private final TodoMvcRepository repo;

    public TodoMvcService(TodoMvcRepository repo) {

        this.repo = repo;
    }

    public Map<String, ?> fetchAttributesForIndexPage() {

        return Map.of("activeCount", repo.activeTodoCount());
    }


    public void createNewTodo(String todo) {

        repo.save(Todo.from(todo));
    }
}
