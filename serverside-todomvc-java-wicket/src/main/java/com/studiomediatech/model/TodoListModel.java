package com.studiomediatech.model;

import java.util.List;

import com.studiomediatech.TodoMVC;
import com.studiomediatech.domain.Todo;

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * A simple list model for todos.
 */
public class TodoListModel extends LoadableDetachableModel<List<Todo>> {

  private static final long serialVersionUID = -5811128247181974532L;

  public TodoListModel() {
    super();
  }

  @Override
  protected List<Todo> load() {
    return TodoMVC.getTodoService().findAll();
  }

}
