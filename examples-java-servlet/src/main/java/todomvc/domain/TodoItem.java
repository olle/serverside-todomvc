package todomvc.domain;

import todomvc.repository.Identifiable;

public class TodoItem implements Identifiable<Long> {

  public enum Status {
    ACTIVE(""),
    COMPLETED("completed"),
    EDITING("editing");

    private final String css;

    Status(String css) {
      this.css = css;
    }

    @Override
    public String toString() {
      return this.css;
    };
  }

  private final String todo;
  private final Long id;
  private final Status status;

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
}
