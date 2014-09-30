package com.studiomediatech.component;

import com.studiomediatech.domain.Todo;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class TodoTextLabel extends Label {

  private static final long serialVersionUID = 3769486362417996955L;

  public TodoTextLabel(String id, IModel<Todo> todoModel) {
    super(id, new PropertyModel<String>(todoModel, "todo"));
  }
}
