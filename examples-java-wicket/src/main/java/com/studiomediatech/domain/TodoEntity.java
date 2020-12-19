package com.studiomediatech.domain;

import java.io.Serializable;

public class TodoEntity implements Serializable {

  private static final long serialVersionUID = -9118514027306307895L;

  private String todo;
  private Status status;

  public TodoEntity() {
    this.status = Status.ACTIVE;
  }

  public TodoEntity(String todo) {
    this.todo = todo;
    this.status = Status.ACTIVE;
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

  @Override
  public String toString() {
    return "TodoEntity [todo=" + todo + ", status=" + status + "]";
  }
}
