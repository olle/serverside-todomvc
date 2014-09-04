package com.studiomediatech.component;

import com.studiomediatech.domain.Todo;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class TodoTextLabelTest {

  @Test
  public void testTodoTextLabel() {

    WicketTester tester = new WicketTester();
    Todo todo = new Todo();
    IModel<Todo> todoModel = new Model<Todo>();
    String id = "id";
    TodoTextLabel label = new TodoTextLabel(id, todoModel);

    todoModel.setObject(null);
    tester.startComponentInPage(label);
    tester.assertLabel(id, "");

    todoModel.setObject(todo);
    tester.startComponentInPage(label);
    tester.assertLabel(id, "");

    todo.setTodo("foo bar");
    tester.startComponentInPage(label);
    tester.assertLabel(id, "foo bar");
  }
}
