package com.studiomediatech.todomvc;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.studiomediatech.todomvc.domain.Todo;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

  private final Set<Todo> todos = new LinkedHashSet<>();

  public List<Todo> findAll() {

    return Lists.newArrayList(this.todos);
  }

  public Todo newTodo() {

    return new Todo();
  }

  public void save(Todo todo) {
    this.todos.add(todo);
  }

  public void toggleCompleted(long id) {
    for (Todo todo : this.todos) {
      if (todo.getId() == id) {
        todo.toggleCompleted();
        this.todos.add(todo);
      }
    }
  }

  public Todo findOne(Long id) {
    for (Todo todo : this.todos) {
      if (todo.getId() == id) {
        return todo;
      }
    }
    throw new IllegalStateException("Cannot edit removed todo");
  }
}
