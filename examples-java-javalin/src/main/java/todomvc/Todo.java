package todomvc;

import java.util.UUID;


public class Todo {

    enum Status {

        ACTIVE,
        COMPLETED
    }

    final UUID id;
    private final String text;
    private final Todo.Status status;
    private final boolean editing;

    private Todo(String text) {

        this.id = UUID.randomUUID();
        this.text = text;
        this.status = Status.ACTIVE;
        this.editing = false;
    }


    private Todo(Todo other) {

        this.id = other.id;
        this.text = other.text;
        this.status = other.status;
        this.editing = other.editing;
    }


    public Todo(Todo other, Todo.Status status) {

        this.id = other.id;
        this.text = other.text;
        this.status = status;
        this.editing = other.editing;
    }

    protected UUID getUUID() {

        return this.id;
    }


    public Todo copy() {

        return new Todo(this);
    }


    public static Todo createNew(String text) {

        return new Todo(text);
    }


    public Todo markAsCompleted() {

        return new Todo(this, Status.COMPLETED);
    }


    public Todo markAsActive() {

        return new Todo(this, Status.ACTIVE);
    }


    public boolean isActive() {

        return status == Status.ACTIVE;
    }


    public boolean isCompleted() {

        return status == Status.COMPLETED;
    }


    public boolean isEditing() {

        return editing;
    }


    public String getId() {

        return id.toString();
    }


    public String getText() {

        return text;
    }
}
