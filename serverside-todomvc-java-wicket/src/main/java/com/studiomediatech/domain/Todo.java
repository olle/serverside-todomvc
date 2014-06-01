package com.studiomediatech.domain;

import java.io.Serializable;

import com.studiomediatech.TodoMVC;

public class Todo implements Serializable {

  private static final long serialVersionUID = -7124778236879155131L;

  private final TodoEntity todo;

  public Todo() {
    this.todo = new TodoEntity();
  }

  public Todo(String todo) {
    this.todo = new TodoEntity(todo);
  }

  public Todo(TodoEntity todoEntity) {
    this.todo = todoEntity;
  }

  /**
   * Saves this todo.
   */
  public void save() {
    TodoMVC.getTodoService().save(this.todo);
  }

  public String getTodo() {
    return this.todo.getTodo();
  }

  public void setTodo(String todo) {
    this.todo.setTodo(todo);
  }

  public boolean isActive() {
    return this.todo.isActive();
  }

  public void setActive() {
    this.todo.setActive();
  }

  public boolean isCompleted() {
    return this.todo.isCompleted();
  }

  public void setCompleted(boolean completed) {
    this.todo.setCompleted(completed);
  }

  public boolean isEditing() {
    return this.todo.isEditing();
  }

}
