package com.studiomediatech.model;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.studiomediatech.domain.Filter;
import com.studiomediatech.domain.Todo;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public class FilteredTodoListModel extends AbstractReadOnlyModel<List<Todo>> {

  private static final long serialVersionUID = 8405044816544788957L;

  private final IModel<List<Todo>> list;
  private final IModel<Filter> filter;

  public FilteredTodoListModel(IModel<List<Todo>> list, IModel<Filter> filter) {
    this.list = list;
    this.filter = filter;
  }

  @Override
  public List<Todo> getObject() {

    Iterable<Todo> filtered = Iterables.filter(this.list.getObject(), new Predicate<Todo>() {

      public boolean apply(Todo input) {
        return FilteredTodoListModel.this.filter.getObject().apply(input);
      }
    });

    return Lists.newArrayList(filtered);
  }
}
