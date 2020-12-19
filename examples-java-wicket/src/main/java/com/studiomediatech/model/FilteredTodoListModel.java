package com.studiomediatech.model;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.wicket.model.IModel;

import com.studiomediatech.domain.Filter;
import com.studiomediatech.domain.Todo;

public class FilteredTodoListModel implements IModel<List<Todo>> {

  private static final long serialVersionUID = 8405044816544788957L;

  private final IModel<List<Todo>> list;
  private final IModel<Filter> filter;

  public FilteredTodoListModel(IModel<List<Todo>> list, IModel<Filter> filter) {
    this.list = list;
    this.filter = filter;
  }

  @Override
  public List<Todo> getObject() {
    return this.list.getObject().stream().filter(this.filter.getObject()::apply).collect(Collectors.toList());
  }
}
