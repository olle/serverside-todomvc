package com.studiomediatech.serverside.todomvc.domain;

/**
 * A task can typically be done, but is most often postponed indefinitely - it
 * appears.
 *
 * @since 0.3.0
 */
public final class Task {

  /**
   * Describes what this task pertains to; what it is that needs to be done.
   */
  public final String description;

  private Task(String description) {
    this.description = description;
  }

  /**
   * Creates a new task with the given description.
   *
   * @param description for the task at hand, what it is that needs to be done
   *
   * @return a new task instance, never {@code null}
   */
  public static Task withDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("A task cannot be created with an empty description");
    }
    return new Task(description);
  }

  @Override
  public boolean equals(Object obj) {
    // NOTE: Since Tasks cannot be extended this should be correct.
    if (obj instanceof Task) {
      return this.description.equals(((Task) obj).description);
    }
    return false;
  }

}
