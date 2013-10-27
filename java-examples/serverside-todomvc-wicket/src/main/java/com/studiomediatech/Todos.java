package com.studiomediatech;

import java.util.List;

import org.apache.wicket.markup.repeater.data.ListDataProvider;

import com.google.common.collect.Lists;
import com.studiomediatech.serverside.todomvc.common.storage.Storage;

public class Todos extends ListDataProvider<Todo> {
    private static final long serialVersionUID = 1L;

    private final Storage<Todo> storage;

    public Todos(Storage<Todo> storage) {
        super();
        this.storage = storage;
    }

    @Override
    protected List<Todo> getData() {
        return Lists.newLinkedList(this.storage.findAll());
    }
}
