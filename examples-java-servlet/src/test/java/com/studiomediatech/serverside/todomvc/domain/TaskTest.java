package com.studiomediatech.serverside.todomvc.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;

import static org.hamcrest.Matchers.*;

public class TaskTest {

  @Test
  public void a_task_returns_its_description() {
    assertThat(Task.withDescription("hello").description, is("hello"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void creating_a_new_task_with_null_description_throws_an_exception() {
    Task.withDescription(null);
  }

  @Test
  public void two_tasks_with_the_same_description_are_considered_equal_and_have_the_same_hash_value() {
    Task t1 = Task.withDescription("foo");
    Task t2 = Task.withDescription("foo");
    assertThat(t1, is(equalTo(t2)));
    assertThat(t1.hashCode(), is(equalTo(t2.hashCode())));
  }

}
