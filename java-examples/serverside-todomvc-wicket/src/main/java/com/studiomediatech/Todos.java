package com.studiomediatech;

import java.util.List;

import com.google.common.collect.Lists;
import com.studiomediatech.serverside.todomvc.common.storage.Repository;

import org.apache.wicket.markup.repeater.data.ListDataProvider;

public class Todos extends ListDataProvider<Todo> {
  private static final long serialVersionUID = 1L;

  private final Repository<Todo, String> storage;

  public Todos(Repository<Todo, String> storage) {
    super();
    this.storage = storage;
  }

  @Override
  protected List<Todo> getData() {
    return Lists.newLinkedList(this.storage.findAll());
  }
}
