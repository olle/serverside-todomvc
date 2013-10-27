package com.studiomediatech.serverside.todomvc.servlet;

import com.studiomediatech.serverside.todomvc.common.storage.Identifiable;

public class Todo implements Identifiable {

  private final String id;

  public Todo(String id) {
    this.id = id;
  }

  @Override
  public String getId() {
    return this.id;
  }
}
