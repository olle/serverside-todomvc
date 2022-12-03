package com.studiomediatech.todomvc;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TodoMvcRepository {

	private final Map<UUID, Todo> todos = new ConcurrentHashMap<>();

	public synchronized void deleteAllWhereActiveTrue() {

		todos.values().stream().filter(Todo::isCompleted).map(Todo::getUUID).toList().forEach(todos::remove);
	}

	public long activeTodoCount() {

		return todos.values().stream().filter(Todo::isActive).count();
	}

	public long completedTodoCount() {

		return todos.values().stream().filter(Todo::isCompleted).count();
	}

	public void save(Todo todo) {

		Todo copy = todo.copy();
		todos.put(copy.getUUID(), copy);
	}

	public Collection<Todo> findAllActiveTodos() {

		return todos.values().stream().filter(Todo::isActive).toList();
	}

	public Collection<Todo> findAllActiveTodosEditing(UUID uuid) {

		return todos.values().stream().filter(Todo::isActive).map(todo -> todo.markAsEditingIfMatches(uuid)).toList();
	}

	public Optional<Todo> findByUUID(UUID uuid) {

		return Optional.ofNullable(todos.get(uuid));
	}

	public Collection<Todo> findAllCompletedTodos() {

		return todos.values().stream().filter(Todo::isCompleted).toList();
	}

	public void deleteByUUID(UUID uuid) {

		todos.remove(uuid);
	}

}
