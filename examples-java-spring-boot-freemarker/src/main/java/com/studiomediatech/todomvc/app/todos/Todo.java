package com.studiomediatech.todomvc.app.todos;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Todo extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	public enum Status {
		ACTIVE, COMPLETED;
	}

	private String text;
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;
	private boolean editing = false;

	Todo() {
		// Required!
	}

	public Todo(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Todo toggleStatus() {
		this.status = status == Status.ACTIVE ? Status.COMPLETED : Status.ACTIVE;
		return this;
	}

	public Status getStatus() {
		return status;
	}

	public boolean isEditing() {
		return editing;
	}

	public Todo edit() {
		this.editing = true;
		return this;
	}

	public Todo cancelEdit() {
		this.editing = false;
		return this;
	}

}
