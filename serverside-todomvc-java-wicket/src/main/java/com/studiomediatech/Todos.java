package com.studiomediatech;

import java.util.Collections;
import java.util.List;

import com.studiomediatech.domain.Todo;

import org.apache.wicket.markup.repeater.data.ListDataProvider;

public class Todos extends ListDataProvider<Todo> {
  private static final long serialVersionUID = 1L;

  public Todos() {
    super();
  }

  @Override
  protected List<Todo> getData() {
    return Collections.emptyList();
  }
}
