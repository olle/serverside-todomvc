package com.studiomediatech.todomvc.app.todos;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Todo extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;
	
	public enum Status {
		ACTIVE,EDITING,COMPLETED;
	}

	private String text;
	private Status status = Status.ACTIVE;

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
	
	public boolean isEditing() {
		return status == Status.EDITING;
	}

}
