package todomvc;

import io.javalin.Javalin;

import io.javalin.http.Context;

import io.javalin.rendering.template.JavalinJte;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;


public class TodoMVC {

    public static void main(String[] args) {

        JavalinJte.init();

        var app = Javalin.create(config -> {
                    config.staticFiles.add("/public");
                    config.plugins.enableDevLogging();
                }).start(8080);

        app.get("/", IndexPage::show);
        app.post("/todos", Todos::handleTodos);
        app.post("/todo", Todos::handleTodo);
    }

    static class IndexPage {

        public static Context show(Context ctx) {

            return ctx.render("index.jte", Todos.asMap());
        }
    }

    static class Todos {

        private static final Map<UUID, Todo> todos = new ConcurrentHashMap<>();

        public static Map<String, ? extends Object> asMap() {

            return Map.of( // NOSONAR
                    "active", todos.values().stream().filter(Todo::isActive).toList(), // NOSONAR
                    "completed", todos.values().stream().filter(Todo::isCompleted).toList(), // NOSONAR
                    "activeCount", todos.values().stream().filter(Todo::isActive).count(), // NOSONAR
                    "completedCount", todos.values().stream().filter(Todo::isCompleted).count());
        }


        public static void handleTodos(Context ctx) {

            if (ctx.formParamMap().containsKey("todo")) {
                addNewTodo(ctx);
            }
        }


        public static void handleTodo(Context ctx) {

            if (ctx.formParamMap().containsKey("complete")) {
                markAsCompleted(ctx);
            }
        }


        private static void markAsCompleted(Context ctx) {

            updateWith(ctx.formParam("complete"), Todo::markAsCompleted);
            ctx.redirect("/");
        }


        private static void updateWith(String id, UnaryOperator<Todo> mapper) {

            Optional.ofNullable(todos.get(UUID.fromString(id)))
                .map(mapper)
                .ifPresent(todo -> {
                    var copy = todo.copy();
                    todos.put(copy.getUUID(), copy);
                });
        }


        private static void addNewTodo(Context ctx) {

            Todo todo = Todo.createNew(ctx.formParam("todo"));
            todos.put(todo.id, todo);
            ctx.redirect("/");
        }
    }

    public static class Todo {

        enum Status {

            ACTIVE,
            COMPLETED
        }

        private final UUID id;
        private final String text;
        private final Status status;
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


        public Todo(Todo other, Status status) {

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
}
