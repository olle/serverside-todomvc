package com.studiomediatech.domain;

import java.io.Serializable;

import com.google.common.base.Objects;

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
    return Objects.toStringHelper(this).add("todo", this.todo).add("status", this.status.name()).toString();
  }
}
