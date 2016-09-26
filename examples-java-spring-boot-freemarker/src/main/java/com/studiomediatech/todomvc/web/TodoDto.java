package com.studiomediatech.todomvc.web;

import com.studiomediatech.todomvc.app.todos.Todo;
import com.studiomediatech.todomvc.app.todos.Todo.Status;

public class TodoDto {

	private final Long id;
	private final String text;
	private final String status;
	private final boolean editing;

	public TodoDto(Todo todo) {
		id = todo.getId();
		text = todo.getText();
		status = todo.getStatus().name().toLowerCase();
		editing = todo.isEditing();
	}

	public boolean isEditing() {
		return editing;
	}

	public boolean isActive() {
		return status.equals(Status.ACTIVE.name().toLowerCase());
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getStatus() {
		return status;
	}

	public boolean isCompleted() {
		return status.equals(Status.COMPLETED.name().toLowerCase());
	}

}
