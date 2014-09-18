package com.studiomediatech.todomvc.support.web;

/**
 * A message to be displayed in web context. Depending on the type, different
 * style will be applied.
 */
public class Message {
  /**
   * Name of the flash attribute.
   */
  public static final String MESSAGE_ATTRIBUTE = "message";

  /**
   * The type of the message to be displayed. The type is used to show message
   * in a different style.
   */
  public static enum Type {
    DANGER,
    WARNING,
    INFO,
    SUCCESS;
  }

  private final String message;
  private final Type type;
  private final Object[] args;

  public Message(String message, Type type) {
    this.message = message;
    this.type = type;
    this.args = null;
  }

  public Message(String message, Type type, Object... args) {
    this.message = message;
    this.type = type;
    this.args = args;
  }

  public String getMessage() {
    return this.message;
  }

  public Type getType() {
    return this.type;
  }

  public Object[] getArgs() {
    return this.args;
  }
}
