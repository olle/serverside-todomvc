package com.studiomediatech.model;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.studiomediatech.domain.Status;
import com.studiomediatech.domain.Todo;

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
    return (int) this.model.getObject()
                           .stream()
                           .filter(todo -> todo.getStatus().equals(Status.COMPLETED))
                           .count();
  }
}