package com.studiomediatech.model;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.studiomediatech.domain.Status;
import com.studiomediatech.domain.Todo;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Provides the count of completed tasks in a given list model.
 */
public class CompletedCountModel extends Model<Integer> {

  private static final long serialVersionUID = -21087615501756965L;

  private final IModel<List<Todo>> model;

  public CompletedCountModel(IModel<List<Todo>> model) {
    this.model = model;
  }

  @Override
  public Integer getObject() {

    return FluentIterable.<Todo> from(this.model.getObject()).filter(new Predicate<Todo>() {

      public boolean apply(Todo todo) {
        return todo.getStatus() == Status.COMPLETED;
      }
    }).size();
  }
}