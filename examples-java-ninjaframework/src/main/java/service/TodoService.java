package service;

import java.util.List;

import model.Todo;

public interface TodoService {

  void create(Todo todo);

  List<Todo> list(String filter);

  int count();

  void delete(String id);

  void update(Todo todo);

  Todo find(String id);
}
