package todomvc.domain;

import todomvc.repository.Identifiable;

public class TodoItem implements Identifiable<Long> {

	private final String todo;
	private final Long id;
	private final Status status;

	public boolean isActive() {

		return this.status == Status.ACTIVE;
	}

	public boolean isNew() {

		return id == null;
	}

	public boolean isEditing() {

		return status == Status.EDITING;
	}

	public boolean isNotEditing() {

		return !isEditing();
	}

	public TodoItem markCompleted() {

		return new TodoItem(this, Status.COMPLETED);
	}

	public enum Status {
		ACTIVE, COMPLETED, EDITING;
	}

	public TodoItem() {
		this("");
	}

	public TodoItem(String todo) {
		this.todo = todo;
		this.id = System.currentTimeMillis();
		this.status = Status.ACTIVE;
	}

	public TodoItem(TodoItem prev, Status newStatus) {
		this.todo = prev.todo;
		this.id = prev.id;
		this.status = newStatus;
	}

	public TodoItem(TodoItem other, long id) {
		this.id = id;
		this.todo = other.todo;
		this.status = other.status;
	}

	public TodoItem(TodoItem other) {
		this.id = other.id;
		this.todo = other.todo;
		this.status = other.status;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getTodo() {
		return this.todo;
	}

	@Override
	public String toString() {
		return this.todo;
	}

	public Status getStatus() {
		return this.status;
	}

	public static TodoItem from(String text) {

		return new TodoItem(text);
	}

	public static TodoItem from(TodoItem entity, long newId) {

		return new TodoItem(entity, newId);
	}

	public static TodoItem from(TodoItem entity) {

		return new TodoItem(entity);
	}

}
