package com.studiomediatech.domain;

import java.io.Serializable;

public class Todo implements Serializable {

  private static final long serialVersionUID = -9118514027306307895L;

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

  private String todo;
  private Status status;

  public Todo() {
    // OK
  }

  public Todo(String todo) {
    this.todo = todo;
    this.status = Status.ACTIVE;
  }

  public String getTodo() {
    return this.todo;
  }

  public void setTodo(String todo) {
    this.todo = todo;
  }

  public boolean isActive() {
    return this.status == Status.ACTIVE;
  }

  public void setActive() {
    this.status = Status.ACTIVE;
  }

  public boolean isCompleted() {
    return this.status == Status.COMPLETED;
  }

  public void setCompleted(boolean completed) {
    if (completed) {
      this.status = Status.COMPLETED;
    }
    else {
      this.status = Status.ACTIVE;
    }
  }

  public boolean isEditing() {
    return this.status == Status.EDITING;
  }

  @Override
  public String toString() {
    return this.todo;
  }

  /**
   * Saves this todo.
   */
  public void save() {
    // TODO Auto-generated method stub

  }
}
