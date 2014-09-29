package com.studiomediatech.todomvc.domain;

import com.google.common.base.Objects;

public class Todo {

  private String todo;
  private Status status;
  private Status lastStatus;
  private long id;

  public Todo() {
    this.status = Status.ACTIVE;
    this.id = System.currentTimeMillis();
  }

  public Todo(String todo) {
    this.todo = todo;
    this.status = Status.ACTIVE;
    this.id = System.currentTimeMillis();
  }

  public String getTodo() {
    return this.todo;
  }

  public void setTodo(String todo) {
    this.todo = todo;
  }

  public Status getStatus() {
    return this.status;
  }

  public boolean isCompleted() {
    return this.status.equals(Status.COMPLETED);
  }

  public boolean isEditing() {
    return this.status.equals(Status.EDITING);
  }

  public String getStatusClass() {
    if (this.status.equals(Status.ACTIVE)) {
      return "item";
    }
    if (this.status.equals(Status.COMPLETED)) {
      return "item completed";
    }
    if (this.status.equals(Status.EDITING)) {
      return "item editing";
    }
    return this.status.toString();
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {

    return Objects.toStringHelper(this)
                  .add("id", this.id)
                  .add("todo", this.todo)
                  .add("status", this.status.name())
                  .toString();
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Todo other = (Todo) obj;
    return Objects.equal(other.id, this.id);
  }

  public void toggleCompleted() {
    if (this.status == Status.COMPLETED) {
      this.status = Status.ACTIVE;
    }
    else {
      this.status = Status.COMPLETED;
    }
  }

  public void beginEditing() {
    this.lastStatus = this.status;
    this.status = Status.EDITING;
  }

  public void endEditing() {
    this.status = this.lastStatus;
    this.lastStatus = null;
  }
}
