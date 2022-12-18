package todomvc.domain;

public class TodoItem {

	private final String todo;
	private final Long id;
	private final Status status;

	public enum Status {
		ACTIVE, COMPLETED, EDITING;
	}

	private TodoItem(String todo, long id) {
		this.todo = todo;
		this.id = id;
		this.status = Status.ACTIVE;
	}

	private TodoItem(TodoItem other, Status newStatus) {
		this.todo = other.todo;
		this.id = other.id;
		this.status = newStatus;
	}

	private TodoItem(TodoItem other) {
		this.todo = other.todo;
		this.id = other.id;
		this.status = other.status;
	}

	private TodoItem(TodoItem other, String todo) {
		this.todo = todo;
		this.id = other.id;
		this.status = Status.ACTIVE;
	}

	public static TodoItem from(String todoText, long newId) {

		return new TodoItem(todoText, newId);
	}

	public Long getId() {
		return this.id;
	}

	public String getTodo() {
		return this.todo;
	}

	public boolean isActive() {

		return this.status == Status.ACTIVE || this.status == Status.EDITING;
	}

	public boolean isCompleted() {

		return this.status == Status.COMPLETED;
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

	public TodoItem markActive() {

		return new TodoItem(this, Status.ACTIVE);
	}

	public TodoItem markEditing() {

		return new TodoItem(this, Status.EDITING);
	}

	public TodoItem copy() {

		return new TodoItem(this);
	}

	public TodoItem update(String todoText) {

		return new TodoItem(this, todoText);
	}

}
