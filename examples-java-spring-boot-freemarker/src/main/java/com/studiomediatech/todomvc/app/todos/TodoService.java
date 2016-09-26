package com.studiomediatech.todomvc.app.todos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studiomediatech.todomvc.app.todos.Todo.Status;

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
		return todoRepository.findAll();
	}

	public void deleteTodo(Long id) {
		todoRepository.delete(id);
	}

	public void toggleStatus(Long id) {
		todoRepository.findOneById(id).map(Todo::toggleStatus).ifPresent(todoRepository::save);
	}

	public void editTodo(Long id) {
		todoRepository.findByEditingTrue().stream().map(Todo::cancelEdit).forEach(todoRepository::save);
		todoRepository.findOneById(id).map(Todo::edit).ifPresent(todoRepository::save);
	}

	public void updateTodo(Long id, String itemText) {
		todoRepository.findOneById(id).map(t -> {
			t.setText(itemText);
			return t;
		}).map(Todo::cancelEdit).ifPresent(todoRepository::save);
	}

	@Transactional
	public void clearCompleted() {
		todoRepository.deleteByStatus(Status.COMPLETED);		
	}
}
