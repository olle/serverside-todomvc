package com.studiomediatech.model;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.studiomediatech.domain.Status;
import com.studiomediatech.domain.Todo;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class LeftCountModel extends Model<Integer> {

  private static final long serialVersionUID = -4272443040742128212L;

  private final IModel<List<Todo>> model;

  public LeftCountModel(IModel<List<Todo>> model) {
    this.model = model;
  }

  @Override
  public Integer getObject() {

    return FluentIterable.from(this.model.getObject()).filter(new Predicate<Todo>() {

      public boolean apply(Todo todo) {
        return todo.getStatus() == Status.ACTIVE;
      }
    }).size();
  }
}
