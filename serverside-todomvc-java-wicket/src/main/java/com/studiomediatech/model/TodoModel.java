package com.studiomediatech.model;

import com.studiomediatech.domain.Todo;

import org.apache.wicket.model.Model;

public class TodoModel extends Model<Todo> {

  private static final long serialVersionUID = -901851188836351307L;

  private Todo transientObject;

  public TodoModel() {
    this.transientObject = new Todo();
  }

  @Override
  public void detach() {
    super.detach();
    this.transientObject = null;
  }

  @Override
  public Todo getObject() {
    return this.transientObject;
  }

}
