package service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import model.Todo;

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
	  
	  final java.util.function.Predicate<Todo> predicate;
	  
	  if ("all".equals(filter)) {
		  predicate = ignored -> true;
	  } else {
		  predicate = todo -> todo.getStatus().equals(filter);
	  }

	  return todos.values().stream().filter(predicate).collect(Collectors.toList());
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
