package com.studiomediatech.todomvc.todos;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

	private final TodoRepository todoRepository;

	@Autowired
	public TodoService(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	public void saveTodo(Todo todo) {
		todoRepository.save(todo);
	}

	public List<Todo> getTodos() {
		return Collections.emptyList();
	}
}
