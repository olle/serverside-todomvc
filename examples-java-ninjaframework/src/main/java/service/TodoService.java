package service;

import java.util.List;

import model.Todo;

public interface TodoService {

  void create(Todo todo);

  List<Todo> list();
}
