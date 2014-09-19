package com.studiomediatech.todomvc.domain;

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