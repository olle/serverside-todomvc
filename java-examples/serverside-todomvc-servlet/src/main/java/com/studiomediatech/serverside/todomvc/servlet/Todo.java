package com.studiomediatech.serverside.todomvc.servlet;

import com.studiomediatech.serverside.todomvc.common.storage.Identifiable;

public class Todo implements Identifiable<Long> {

  private final String todo;
  private final Long id;

  public Todo() {
    this("");
  }

  public Todo(String todo) {
    this.todo = todo;
    this.id = System.currentTimeMillis();
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

}
