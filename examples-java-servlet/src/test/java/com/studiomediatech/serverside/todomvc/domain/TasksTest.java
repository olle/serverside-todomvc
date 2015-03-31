package com.studiomediatech.serverside.todomvc.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;

import static org.hamcrest.Matchers.*;

public class TasksTest {

  @Test
  public void a_task_returns_its_description() {
    Task task = Task.withDescription("hello");
    assertThat(task.description, is("hello"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void creating_a_new_task_with_null_description_throws_an_exception() {
    Task.withDescription(null);
  }

  @Test
  public void returns_an_empty_tasks_list_after_construction() {
    Tasks tasks = new Tasks();
    assertThat(tasks.list().size(), equalTo(0));
  }

  @Test
  public void contains_a_task_that_as_added() {
    Tasks tasks = new Tasks();
    tasks.add(Task.withDescription("Paint the sky"));
    assertThat(tasks.list().size(), equalTo(1));
    assertThat(tasks.list(), contains(Task.withDescription("Paint the sky")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void tasks_throws_when_trying_to_add_null() {
    new Tasks().add(null);
  }

  @Test
  public void two_tasks_with_the_same_description_are_considered_equal() {
    assertThat(Task.withDescription("foo"), is(equalTo(Task.withDescription("foo"))));
  }

  @Test
  public void tasks_have_uids() {
    Task foo = Task.withDescription("foo");
    Task bar = Task.withDescription("bar");
    assertThat(foo.uuid, is(not(equalTo(bar.uuid))));

    Task fooAgain = Task.withDescription("foo");
    assertThat(fooAgain.uuid, is(equalTo(foo.uuid)));
  }

  @Test
  public void adding_and_removing_tasks_is_possible() {
    Tasks tasks = new Tasks();
    Task hello = Task.withDescription("hello");
    tasks.add(hello);
    assertThat(tasks.list().size(), equalTo(1));
    assertThat(tasks.list(), contains(hello));
    tasks.remove(hello);
    assertThat(tasks.list().size(), equalTo(0));
    assertThat(tasks.list(), not(contains(hello)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void tasks_throw_when_trying_to_remove_null() {
    new Tasks().remove(null);
  }

}
