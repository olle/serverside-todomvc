package todomvc.domain;

import java.util.UUID;


public final class Todo {

    private final UUID id;
    private final String todo;
    private final boolean active;
    private boolean editing;

    private Todo(String todo) {

        this.id = UUID.randomUUID();
        this.todo = todo;
        this.active = true;
    }


    private Todo(Todo other) {

        this.id = other.id;
        this.todo = other.todo;
        this.active = other.active;
    }

    public static Todo newFrom(String todo) {

        return new Todo(todo);
    }


    public boolean isActive() {

        return active;
    }


    public boolean isEditing() {

        return editing;
    }


    public UUID getId() {

        return id;
    }


    public String getTodo() {

        return todo;
    }


    public Todo copy() {

        return new Todo(this);
    }
}
