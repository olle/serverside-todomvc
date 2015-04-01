package com.studiomediatech.serverside.todomvc.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;

import static org.hamcrest.Matchers.*;

// DESIGN
// =======
// [CHECK] tasks.add("Buy oranges");
// [CHECK] tasks.clear(42);
// [CHECK] tasks.change(11, "Buy apples");
// tasks.complete(3);
// tasks.list();
// tasks.list(Predicates.completed());
// tasks.complete();
// tasks.clear(Predicates.completed());

public class TasksTest {

  @Test
  public void we_can_create_a_new_task_list() {
    assertThat(Tasks.newList(), is(notNullValue(Tasks.class)));
  }

  @Test
  public void show_tasks_returns_an_empty_collection_for_new_task_list() {
    assertThat(Tasks.newList().list(), is(emptyCollectionOf(Task.class)));
  }

  @Test
  public void we_can_add_a_task_to_the_list() {
    assertThat(Tasks.newList().add("Buy oranges").list(), contains(Task.withDescription("Buy oranges")));
  }

  @Test
  public void we_can_clear_a_specific_task_by_its_number_in_the_list() {
    Tasks tasks = Tasks.newList().add("foo").add("bar");
    Task foo = Task.withDescription("foo");
    Task bar = Task.withDescription("bar");
    assertThat(tasks.list(), contains(foo, bar));
    tasks.clear(0);
    assertThat(tasks.list(), not(contains(foo)));
    assertThat(tasks.list(), contains(bar));
    assertThat(tasks.list().size(), is(1));
  }

  @Test
  public void we_can_change_a_specific_task_description_in_the_list() {
    Task foo = Task.withDescription("foo");
    Task bar = Task.withDescription("bar");
    Task foobar = Task.withDescription("foobar");
    Tasks tasks = Tasks.newList().add("foo").add("bar");
    assertThat(tasks.list(), contains(foo, bar));
    tasks.change(1, "foobar");
    assertThat(tasks.list(), contains(foo, foobar));
    assertThat(tasks.list(), not(contains(bar)));
    assertThat(tasks.list().size(), is(2));
  }

}
