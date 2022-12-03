package com.studiomediatech.todomvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TodoMvcService {

	private final TodoMvcRepository repo;

	public TodoMvcService(TodoMvcRepository repo) {

		this.repo = repo;
	}

	public Map<String, ?> fetchAttributesForIndexPage(Boolean hideCompleted) {

		Map<String, Object> map = new HashMap<>();

		map.put("hideCompleted", hideCompleted.booleanValue());
		map.put("activeCount", repo.activeTodoCount());
		map.put("completedCount", repo.completedTodoCount());
		map.put("active", repo.findAllActiveTodos());
		
		if (!hideCompleted) {
			map.put("completed", repo.findAllCompletedTodos());
		}

		return map;
	}

	public Map<String, ?> fetchAttributesForEditPage(String uuid) {

		Map<String, Object> map = new HashMap<>();

		map.put("activeCount", repo.activeTodoCount());
		map.put("completedCount", repo.completedTodoCount());
		map.put("active", repo.findAllActiveTodosEditing(UUID.fromString(uuid)));
		map.put("completed", repo.findAllCompletedTodos());

		return map;
	}

	public void createNewTodo(String todo) {

		repo.save(Todo.from(todo));
	}

	public void updateTodo(String uuid, String update) {

		repo.findByUUID(UUID.fromString(uuid)).map(todo -> todo.update(update)).ifPresent(repo::save);
	}

	public void deleteTodo(String uuid) {

		repo.deleteByUUID(UUID.fromString(uuid));
	}

	public void markTodoAsCompleted(String uuid) {

		repo.findByUUID(UUID.fromString(uuid)).map(Todo::markAsCompleted).ifPresent(repo::save);
	}

	public void markTodoAsActive(String uuid) {

		repo.findByUUID(UUID.fromString(uuid)).map(Todo::markAsActive).ifPresent(repo::save);
	}

	public void clearCompletedTodos() {

		repo.deleteAllWhereActiveTrue();
	}

}
