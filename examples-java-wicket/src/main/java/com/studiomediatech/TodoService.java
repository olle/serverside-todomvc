package com.studiomediatech;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.studiomediatech.domain.Status;
import com.studiomediatech.domain.Todo;
import com.studiomediatech.domain.TodoEntity;

/**
 * No. Not. Thread safe.
 */
public class TodoService {

  private final Set<TodoEntity> todos;

  public TodoService() {
    this.todos = new LinkedHashSet<TodoEntity>();
  }

  public List<Todo> findAll() {
    return this.todos.stream().map(Todo::new).collect(Collectors.toList());
  }

  public void save(TodoEntity todo) {
    this.todos.add(todo);
  }

  public void clearCompleted() {
    this.todos.stream().filter(todo -> todo.getStatus().equals(Status.COMPLETED)).forEach(todos::remove);
  }

  public void delete(TodoEntity todo) {
    this.todos.remove(todo);
  }

  public void toggleAllCompleted(Boolean isCompleted) {
    if (isCompleted) {
      setStatuOnAll(Status.COMPLETED);
    }
    else {
      setStatuOnAll(Status.ACTIVE);
    }
  }

  private void setStatuOnAll(Status status) {
    this.todos.forEach(todo -> todo.setStatus(status));
  }
}
