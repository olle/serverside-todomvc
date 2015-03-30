package com.studiomediatech.serverside.todomvc.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A simple collection of tasks or: things that needs to be done.
 *
 * @since 0.3.0
 */
public final class Tasks {

  private final Collection<Task> tasks = new ArrayList<>();

  /**
   * Retrieves the list of things to be done.
   *
   * @return a read-only list of tasks, never {@code null}
   */
  public Collection<Task> list() {
    return Collections.unmodifiableCollection(this.tasks);
  }

  /**
   * Adds the given task to this list.
   *
   * @param task to add, may not be {@code null}
   * @throws IllegalArgumentException if the given task is {@code null}
   */
  public void add(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("An empty task cannot be added to a todo-list");
    }
    this.tasks.add(task);
  }

}
