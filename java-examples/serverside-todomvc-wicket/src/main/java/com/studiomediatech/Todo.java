package com.studiomediatech;

import java.io.Serializable;

import com.studiomediatech.serverside.todomvc.common.storage.Identifiable;

public class Todo implements Serializable, Identifiable {
  private static final long serialVersionUID = 1L;

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

  private String id;
  private String todo;
  private Status status;

  public Todo() {
    // OK
  }

  public Todo(String todo) {
    this.todo = todo;
    this.status = Status.ACTIVE;
  }

  @Override
  public String getId() {
    return this.id;
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

  @Override
  public String toString() {
    return this.todo;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.todo == null ? 0 : this.todo.hashCode());
    return result;
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
    if (this.todo == null) {
      if (other.todo != null) {
        return false;
      }
    }
    else if (!this.todo.equals(other.todo)) {
      return false;
    }
    return true;
  }
}
