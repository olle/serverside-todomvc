package com.studiomediatech;

import java.util.HashMap;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.studiomediatech.domain.Todo;
import com.studiomediatech.domain.TodoEntity;

public class TodoService {

  private final HashMap<String, TodoEntity> todos;

  public TodoService() {
    this.todos = new HashMap<String, TodoEntity>();
  }

  public List<Todo> findAll() {

    return Lists.newArrayList(Iterables.transform(this.todos.values(), new Function<TodoEntity, Todo>() {

      @Override
      public Todo apply(TodoEntity todoEntity) {
        return new Todo(todoEntity);
      }
    }));
  }

  public void save(TodoEntity todo) {
    this.todos.put(todo.getTodo(), todo);
  }
}
