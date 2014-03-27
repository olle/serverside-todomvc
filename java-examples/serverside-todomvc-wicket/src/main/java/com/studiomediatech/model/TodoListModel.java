package com.studiomediatech.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.studiomediatech.Todo;
import com.studiomediatech.TodoMVC;
import com.studiomediatech.serverside.todomvc.common.storage.Repository;

import org.apache.wicket.Session;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * A simple list model for todos.
 */
public class TodoListModel extends AbstractReadOnlyModel<List<Todo>> {

  private static final long serialVersionUID = -5811128247181974532L;

  private final Repository<Todo, String> storage;

  public TodoListModel() {
    this(TodoMVC.getStorage(Session.get()));
  }

  TodoListModel(Repository<Todo, String> storage) {
    super();
    this.storage = storage;
  }

  @Override
  public List<Todo> getObject() {
    return Lists.newArrayList(this.storage.findAll());
  }

}
