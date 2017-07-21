package com.studiomediatech.serversidetodomvc.experimentsjavaspringdatamap;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

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
	public void addNewTodo(MultiValueMap<String, Object> data) {
		logger().debug("Saving new todo from {}", data);
		todoRepository.saveTodo(data);
	}
}