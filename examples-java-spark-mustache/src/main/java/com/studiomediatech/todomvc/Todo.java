package com.studiomediatech.todomvc;

import java.util.Objects;

public class Todo {

  private static final String COMPLETED = "item completed";
  private static final String NORMAL = "item";

  private final String todo;
  private final String status;
  private final boolean editing;
  private final String id;

  public Todo(String todo) {
    this.todo = todo;
    this.status = NORMAL;
    this.id = String.format("%s", System.currentTimeMillis());
    this.editing = false;
  }

  public Todo(Todo old) {
    this.todo = old.todo;
    this.status = old.status;
    this.id = old.id;
    this.editing = !old.editing;
  }

  public Todo(Todo old, String status) {
    this(old, old.todo, status);
  }

  public Todo(Todo old, String todo, String status) {
    this.todo = todo;
    this.status = status;
    this.id = old.id;
    this.editing = old.editing;
  }

  public String getTodo() {
    return this.todo;
  }

  public String getStatus() {
    if (this.editing) {
      return "item editing";
    }
    return this.status;
  }

  public String getId() {
    return this.id;
  }

  public Todo toggleStatus() {
    if (this.status.equals(NORMAL)) {
      return new Todo(this, this.todo, COMPLETED);
    }
    return new Todo(this, this.todo, NORMAL);
  }

  public Todo toggleEdit() {
    return new Todo(this);
  }

  public Todo updateTodo(String todo) {
    return new Todo(this, todo, this.status);
  }

  public String getChecked() {
    return this.status.equals(NORMAL) ? "" : "is-active";
  }

  public String getInverse() {
    return this.status.equals(NORMAL) ? "completed" : "active";
  }

  public boolean isEditing() {
    return this.editing;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.todo, this.status, this.id);
  }

  public Todo markCompleted() {
    return new Todo(this, COMPLETED);
  }

  public boolean isActive() {
    return this.status.equals(NORMAL);
  }

  public boolean isCompleted() {
    return !isActive();
  }

}
