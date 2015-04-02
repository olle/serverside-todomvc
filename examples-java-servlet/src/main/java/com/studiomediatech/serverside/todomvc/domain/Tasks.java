package com.studiomediatech.serverside.todomvc.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A simple collection of tasks or: things that needs to be done.
 *
 * @since 0.3.0
 */
public final class Tasks {

	private final List<Task> tasks = new ArrayList<>();

	/**
	 * Create a new task list instance.
	 *
	 * @return a new task list, never ever {@code null}
	 */
	public static Tasks newList() {

		return new Tasks();
	}

	/**
	 * Retrieve the list of things to do.
	 *
	 * @return a collection of tasks, or an empty list, never {@code null}
	 */
	public Collection<Task> list() {

		return Collections.unmodifiableCollection(this.tasks);
	}

	/**
	 * Retrieves a list of tasks, filtered by a given predicate.
	 *
	 * @param predicate
	 *            to filter by
	 *
	 * @return a collection of tasks, or an empty list, never {@code null}
	 */
	public Collection<Task> list(Predicate<Task> predicate) {

		return Collections.unmodifiableCollection(this.tasks.stream()
				.filter(predicate).collect(Collectors.toList()));
	}

	/**
	 * Add a task to this list.
	 *
	 * @param description
	 *            of what needs to be done
	 *
	 * @return this list of things to do, with the added task appended to the
	 *         end, never {@code null}
	 */
	public Tasks add(String description) {

		this.tasks.add(Task.withDescription(description));

		return this;
	}

	/**
	 * Removes the task at the given position in the list.
	 *
	 * @param index
	 *            of the task to remove
	 *
	 * @return this list of things to do, without the removed task
	 */
	public Tasks clear(int index) {

		this.tasks.remove(index);

		return this;
	}

	/**
	 * Removes all the tasks from this list that matching a given.
	 * 
	 * @param predicate
	 *            identifying the tasks to remove
	 * 
	 * @return the list of things to do, without the cleared elements, never
	 *         {@code null}
	 */
	public Tasks clear(Predicate<Task> predicate) {

		this.tasks.removeIf(predicate);

		return this;
	}

	/**
	 * Updates the task at the given position in the list, changing its
	 * description to the provided new one.
	 *
	 * @param index
	 *            of the task to change
	 * @param newDescription
	 *            of the task at hand
	 *
	 * @return this list of things to do, with the changed task
	 */
	public Tasks change(int index, String newDescription) {

		this.tasks.add(index, Task.withDescription(newDescription));
		this.tasks.remove(index + 1);

		return this;
	}

	/**
	 * Returns a filter for completed tasks.
	 *
	 * @return a predicate filter, never {@code null}
	 */
	public static Predicate<Task> completed() {

		return task -> task.completed;
	}

	/**
	 * Mark the task at the given position in the list as being completed.
	 *
	 * @param index
	 *            of the task to mark as completed
	 *
	 * @return this list of things to do
	 */
	public Tasks complete(int index) {

		this.tasks.add(index, this.tasks.get(index).isCompleted());
		this.tasks.remove(index + 1);

		return this;
	}

	/**
	 * Marks all tasks in this list as completed
	 * 
	 * @return the list of things to do, with all items marked as completed, or
	 *         an empty list, but never {@code null}
	 */
	public Tasks complete() {

		this.tasks.replaceAll(task -> task.isCompleted());

		return this;
	}

}
