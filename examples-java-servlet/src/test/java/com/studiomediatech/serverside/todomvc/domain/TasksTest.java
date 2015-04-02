package com.studiomediatech.serverside.todomvc.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;


// DESIGN
// =======
// [CHECK] tasks.add("Buy oranges");
// [CHECK] tasks.clear(42);
// [CHECK] tasks.change(11, "Buy apples");
// [CHECK] tasks.complete(3);
// [CHECK] tasks.list();
// [CHECK] tasks.list(Predicates.completed());
// [CHECK] tasks.complete();
// [CHECK] tasks.clear(Predicates.completed());

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


    @Test
    public void we_can_mark_tasks_as_completed_in_the_list_and_show_them() {

        Tasks tasks = Tasks.newList().add("Buy oranges");
        assertThat(tasks.list().size(), is(1));
        assertThat(tasks.list(Tasks.completed()).size(), is(0));
        tasks.complete(0);
        assertThat(tasks.list(Tasks.completed()).size(), is(1));
    }


    @Test
    public void we_can_mark_all_tasks_as_completed_at_once() {

        Tasks tasks = Tasks.newList().add("foo").add("bar").add("foobar");
        assertThat(tasks.list(Tasks.completed()).size(), is(0));
        tasks.complete();
        assertThat(tasks.list(Tasks.completed()).size(), is(3));
    }


    @Test
    public void we_can_clear_tasks_by_predicate() {

        Tasks tasks = Tasks.newList().add("foo").add("bar").add("foobar").complete(1).complete(2);
        assertThat(tasks.list().size(), is(3));
        assertThat(tasks.list(Tasks.completed()).size(), is(2));
        tasks.clear(Tasks.completed());
        assertThat(tasks.list().size(), is(1));
        assertThat(tasks.list(Tasks.completed()).size(), is(0));
    }
}
