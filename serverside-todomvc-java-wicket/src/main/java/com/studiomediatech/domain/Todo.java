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

  public void save() {
    TodoMVC.getTodoService().save(this.todo);
  }

  public void toggleComplete() {
    if (this.todo.getStatus() == Status.COMPLETED) {
      this.todo.setStatus(Status.ACTIVE);
    }
    else {
      this.todo.setStatus(Status.COMPLETED);
    }
  }

  public void delete() {
    TodoMVC.getTodoService().delete(this.todo);
  }

  // DELEGATE METHODS

  public String getTodo() {
    return this.todo.getTodo();
  }

  public void setTodo(String todo) {
    this.todo.setTodo(todo);
  }

  public Status getStatus() {
    return this.todo.getStatus();
  }

  public void setStatus(Status status) {
    this.todo.setStatus(status);
  }

  @Override
  public String toString() {
    return this.todo.toString();
  }

}
