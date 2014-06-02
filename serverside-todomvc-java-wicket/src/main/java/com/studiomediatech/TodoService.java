package com.studiomediatech;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.studiomediatech.domain.Filter;
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

  public List<Todo> findAll(final Filter filter) {

    Collection<TodoEntity> allTodos = this.todos.values();

    Iterable<TodoEntity> filteredTodos = Iterables.filter(allTodos, new Predicate<TodoEntity>() {

      public boolean apply(TodoEntity todo) {
        return filter.getStatus().contains(todo.getStatus());
      }
    });

    Iterable<Todo> transformedTodos = Iterables.transform(filteredTodos, new Function<TodoEntity, Todo>() {

      public Todo apply(TodoEntity todoEntity) {
        return new Todo(todoEntity);
      }
    });

    return Lists.newArrayList(transformedTodos);
  }

  public void save(TodoEntity todo) {
    this.todos.put(todo.getTodo(), todo);
  }

  public void markAllCompleted() {
    for (TodoEntity entity : this.todos.values()) {
      entity.setStatus(Status.COMPLETED);
    }
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
}
