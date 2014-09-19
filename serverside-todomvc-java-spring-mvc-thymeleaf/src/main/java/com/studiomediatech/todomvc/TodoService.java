package com.studiomediatech.todomvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.studiomediatech.todomvc.domain.Status;
import com.studiomediatech.todomvc.domain.TodoEntity;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

  private static final Set<TodoEntity> todos = new HashSet<>();

  public List<TodoEntity> findAll() {

    TodoEntity todo = new TodoEntity();
    todo.setStatus(Status.ACTIVE);
    todo.setTodo("Make more todos");

    return Arrays.asList(todo);
  }

  public TodoEntity newTodo() {

    return new TodoEntity();
  }

  public void save(TodoEntity todo) {
    TodoService.todos.add(todo);
  }
}
