package com.studiomediatech.todomvc;

import java.util.UUID;


public class Todo {

    protected enum Status {

        ACTIVE,
        COMPLETED;
    }

    private final String id;
    private final String todo;
    private final Status status;
    private final boolean editing;

    private Todo(String todo) {

        this.todo = todo;
        this.status = Status.ACTIVE;
        this.id = UUID.randomUUID().toString();
        this.editing = false;
    }


    private Todo(Todo other, Status status) {

        this.id = other.id;
        this.todo = other.todo;
        this.status = status;
        this.editing = other.editing;
    }

    public String getId() {

        return this.id;
    }


    public String getTodo() {

        return this.todo;
    }


    public boolean isEditing() {

        return this.editing;
    }


    public Todo markCompleted() {

        return new Todo(this, Status.COMPLETED);
    }


    public Todo markActive() {

        return new Todo(this, Status.ACTIVE);
    }


    public boolean isActive() {

        return this.status == Status.ACTIVE;
    }


    public boolean isCompleted() {

        return this.status == Status.COMPLETED;
    }


    public static Todo valueOf(String todo) {

        return new Todo(todo);
    }
}
