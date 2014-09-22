package com.studiomediatech.todomvc;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.studiomediatech.todomvc.domain.Todo;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

  private static final Set<Todo> todos = new LinkedHashSet<>();

  public List<Todo> findAll() {

    return Lists.newArrayList(todos);
  }

  public Todo newTodo() {

    return new Todo();
  }

  public void save(Todo todo) {
    TodoService.todos.add(todo);
  }
}
