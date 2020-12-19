package com.studiomediatech.model;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.studiomediatech.domain.Status;
import com.studiomediatech.domain.Todo;

public class LeftCountModel extends Model<Integer> {

  private static final long serialVersionUID = -4272443040742128212L;

  private final IModel<List<Todo>> model;

  public LeftCountModel(IModel<List<Todo>> model) {
    this.model = model;
  }

  @Override
  public Integer getObject() {
    return (int) this.model.getObject().stream().filter(todo -> todo.getStatus().equals(Status.ACTIVE)).count();
  }
}
