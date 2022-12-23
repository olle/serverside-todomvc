package todomvc;

import io.javalin.Javalin;

import io.javalin.http.Context;

import io.javalin.rendering.template.JavalinJte;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class TodoMVC {

    public static void main(String[] args) {

        JavalinJte.init();

        var app = Javalin.create(config -> {
                    config.staticFiles.add("/public");
                    config.plugins.enableDevLogging();
                }).start(8080);

        app.get("/", IndexPage::show);
        app.post("/todos", Todos::handleTodos);
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
                    "activeCount", todos.values().stream().filter(Todo::isActive).count());
        }


        public static void handleTodos(Context ctx) {

            if (ctx.formParamMap().containsKey("todo")) {
                addNewTodo(ctx);
            }

            ctx.redirect("/");
        }


        private static void addNewTodo(Context ctx) {

            Todo todo = Todo.createNew(ctx.formParam("todo"));
            todos.put(todo.id, todo);
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

        public static Todo createNew(String text) {

            return new Todo(text);
        }


        public boolean isActive() {

            return status == Status.ACTIVE;
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
