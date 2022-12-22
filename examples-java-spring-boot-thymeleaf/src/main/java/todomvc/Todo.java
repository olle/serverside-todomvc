package todomvc;

import java.util.UUID;


public final class Todo {

    public enum Status {

        ACTIVE,
        COMPLETED;
    }

    private final UUID id;
    private final String todo;
    private final Status status;
    private final boolean editing;

    private Todo(String todo) {

        this.id = UUID.randomUUID();
        this.todo = todo;
        this.status = Status.ACTIVE;
        this.editing = false;
    }


    private Todo(Todo other) {

        this.id = other.id;
        this.todo = other.todo;
        this.status = other.status;
        this.editing = other.editing;
    }


    private Todo(Todo other, Status status) {

        this.id = other.id;
        this.todo = other.todo;
        this.status = status;
        this.editing = other.editing;
    }

    public static Todo newFrom(String todo) {

        return new Todo(todo);
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


    public Status getStatus() {

        return status;
    }


    public Todo copy() {

        return new Todo(this);
    }


    public Todo markAsCompleted() {

        return new Todo(this, Status.COMPLETED);
    }
}
