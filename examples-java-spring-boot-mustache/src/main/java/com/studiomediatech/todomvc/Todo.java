package com.studiomediatech.todomvc;

import java.util.UUID;

public final class Todo {

	private final UUID uuid;
	private final String todo;
	private final boolean active;

	private boolean editing;

	private Todo(UUID uuid, String todo) {

		this.uuid = uuid;
		this.todo = todo;
		this.active = true;
	}

	private Todo(Todo other, String todo) {

		this.uuid = UUID.fromString(other.uuid.toString());
		this.todo = todo;
		this.active = other.active;
	}

	private Todo(Todo other) {

		this.uuid = UUID.fromString(other.uuid.toString());
		this.todo = other.todo;
		this.active = other.active;
	}

	private Todo(Todo other, boolean active) {

		this.uuid = other.uuid;
		this.todo = other.todo;
		this.active = active;
	}

	public boolean isActive() {

		return active;
	}

	public boolean isCompleted() {

		return !active;
	}

	public boolean isEditing() {

		return editing;
	}

	public static Todo from(String todo) {

		return new Todo(UUID.randomUUID(), todo);
	}

	public UUID getUUID() {

		return this.uuid;
	}

	public String getTodo() {

		return this.todo;
	}

	public Todo markAsCompleted() {

		return new Todo(this, false);
	}

	public Todo markAsActive() {

		return new Todo(this, true);
	}

	public Todo markAsEditingIfMatches(UUID uuid) {

		this.editing = this.uuid.equals(uuid);

		return this;
	}

	public Todo update(String update) {

		return new Todo(this, update);
	}

	public Todo copy() {

		return new Todo(this);
	}

}
