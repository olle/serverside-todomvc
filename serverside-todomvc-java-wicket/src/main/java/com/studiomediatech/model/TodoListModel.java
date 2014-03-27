package com.studiomediatech.model;

import java.util.Collections;
import java.util.List;

import com.studiomediatech.domain.Todo;

import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * A simple list model for todos.
 */
public class TodoListModel extends AbstractReadOnlyModel<List<Todo>> {

  private static final long serialVersionUID = -5811128247181974532L;

  public TodoListModel() {
    super();
  }

  @Override
  public List<Todo> getObject() {
    return Collections.emptyList();
  }

}
