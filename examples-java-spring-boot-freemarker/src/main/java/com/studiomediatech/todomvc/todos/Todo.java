package com.studiomediatech.todomvc.todos;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Todo extends AbstractPersistable<Long> {
	
	private static final long serialVersionUID = 1L;
	
	private String text;

	public Todo(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	

}
