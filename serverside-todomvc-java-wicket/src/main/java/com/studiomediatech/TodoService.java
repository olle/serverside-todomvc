package com.studiomediatech;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.studiomediatech.domain.Status;
import com.studiomediatech.domain.Todo;
import com.studiomediatech.domain.TodoEntity;

/**
 * No. Not. Thread safe.
 */
public class TodoService {

  private final HashMap<String, TodoEntity> todos;

  public TodoService() {
    this.todos = new HashMap<String, TodoEntity>();
  }

  public List<Todo> findAll() {
    return Lists.newArrayList(Iterables.transform(this.todos.values(), new Function<TodoEntity, Todo>() {

      public Todo apply(TodoEntity todoEntity) {
        return new Todo(todoEntity);
      }
    }));
  }

  public void save(TodoEntity todo) {
    this.todos.put(todo.getTodo(), todo);
  }

  public void clearCompleted() {
    Iterator<TodoEntity> it = this.todos.values().iterator();
    while (it.hasNext()) {
      TodoEntity entity = it.next();
      if (entity.getStatus() == Status.COMPLETED) {
        it.remove();
      }
    }
  }

  public void delete(TodoEntity todo) {
    this.todos.remove(todo.getTodo());
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
    Iterator<TodoEntity> it = this.todos.values().iterator();
    while (it.hasNext()) {
      TodoEntity entity = it.next();
      entity.setStatus(status);
    }
  }
}
