package com.studiomediatech.todomvc;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class TodoService {

  private static final Map<String, Todo> todos = new LinkedHashMap<>();

  private static String filter = "all";

  public List<Todo> getTodos() {
    if (filter.equals("active")) {
      return todos.values().stream().filter((t) -> t.isActive()).collect(Collectors.toList());
    }
    else if (filter.equals("completed")) {
      return todos.values().stream().filter((t) -> t.isCompleted()).collect(Collectors.toList());
    }

    return Lists.newArrayList(todos.values());
  }

  public void addNewTodo(String todo) {
    Todo t = new Todo(todo);
    todos.put(t.getId(), t);
  }

  public void toggleStatus(String id) {
    todos.put(id, todos.get(id).toggleStatus());
  }

  public void deleteTodo(String id) {
    todos.remove(id);
  }

  public void editTodo(String id) {
    todos.put(id, todos.get(id).toggleEdit());
  }

  public void updateTodo(String id, String todo) {
    todos.put(id, todos.get(id).updateTodo(todo).toggleEdit());
  }

  public void markAllCompleted() {
    todos.replaceAll((id, t) -> t.markCompleted());
  }

  public void clearCompleted() {
    Iterator<Todo> it = todos.values().iterator();
    while (it.hasNext()) {
      if (it.next().isCompleted()) {
        it.remove();
      }
    }
  }

  public String getFilter() {
    return filter;
  }

  public boolean isFilter(String f) {
    return filter.equals(f);
  }

  public void setFilter(String filter) {
    TodoService.filter = filter;
  }

  public boolean hasTodos() {
    return !todos.isEmpty();
  }

  public boolean hasFilteredTodos() {
    if (filter.equals("active")) {
      return todos.values().stream().filter((t) -> t.isActive()).count() > 0;
    }
    else if (filter.equals("completed")) {
      return todos.values().stream().filter((t) -> t.isCompleted()).count() > 0;
    }
    return hasTodos();
  }
}
