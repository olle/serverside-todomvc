package service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import model.Todo;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
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
  public List<Todo> list(final String filter) {
    final Predicate<Todo> predicate;
    if ("all".equals(filter)) {
      predicate = Predicates.alwaysTrue();
    }
    else {
      predicate = new Predicate<Todo>() {

        @Override
        public boolean apply(Todo input) {
          return input.getStatus().equals(filter);
        }
      };
    }
    return FluentIterable.from(Lists.newArrayList(todos.values())).filter(predicate).toList();
  }

  @Override
  public int count() {
    return todos.size();
  }

  @Override
  public void delete(String id) {
    todos.remove(id);
  }

  @Override
  public void update(Todo todo) {
    Todo t = todos.get(todo.getId());
    t.setTodo(todo.getTodo());
  }

  @Override
  public Todo find(String id) {
    return todos.get(id);
  }

}
