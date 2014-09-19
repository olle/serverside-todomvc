package com.studiomediatech.todomvc.domain;

import com.google.common.base.Objects;

public class TodoEntity {

  private String todo;
  private Status status;

  private final long id;

  public TodoEntity() {

    this.status = Status.ACTIVE;
    this.id = System.currentTimeMillis();
  }

  public TodoEntity(String todo) {

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

  public void setStatus(Status status) {
    this.status = status;
  }

  public long getId() {

    return this.id;
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

    TodoEntity other = (TodoEntity) obj;
    return Objects.equal(other.id, this.id);
  }

}
