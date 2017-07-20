package com.studiomediatech.serversidetodomvc.experimentsjavaspringdatamap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
class TodoServiceImpl implements TodoService, Loggable {

	private final TodoRepository todoRepository;

	public TodoServiceImpl(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	@Override
	public Collection<Todo> getAllTodos() {		
		logger().debug("Fetching all current todos");
		Collection<Todo> todos = todoRepository.findAll();
		logger().debug("Found {} todos in all", todos.size());
		return todos;
	}

	@Override
	public void addNewTodo(Map<String, Object> data) {
		todoRepository.saveTodo(data);
	}
}