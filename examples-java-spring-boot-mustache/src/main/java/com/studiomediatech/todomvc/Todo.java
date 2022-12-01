package com.studiomediatech.todomvc;

import java.util.UUID;


public final class Todo {

    private final UUID uuid;
    private final String todo;
    private final boolean active;

    private Todo(UUID uuid, String todo) {

        this.uuid = uuid;
        this.todo = todo;
        this.active = true;
    }

    public boolean isActive() {

        return active;
    }


    public static Todo from(String todo) {

        return new Todo(UUID.randomUUID(), todo);
    }


    public UUID getUUID() {

        return this.uuid;
    }
}
