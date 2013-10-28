package com.studiomediatech.serverside.todomvc.servlet;

import com.studiomediatech.serverside.todomvc.common.storage.Identifiable;

public class Todo implements Identifiable<String> {

  public Todo() {
    // OK
  }

  @Override
  public String getId() {
    return Integer.toString(hashCode());
  }
}
