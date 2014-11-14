package service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import model.Todo;

import com.google.common.collect.Lists;

@Singleton
public class InMemoryTodoService implements TodoService {

  // The most simple in-memory storage there is (and ordered too)
  private static final Map<String, Todo> todos = new LinkedHashMap<>();

  @Override
  public void create(Todo todo) {

    todo.setId("" + System.currentTimeMillis());

    todos.put(todo.getId(), todo);
  }

  @Override
  public List<Todo> list(String filter) {

    return Lists.newArrayList(todos.values());
  }
}
