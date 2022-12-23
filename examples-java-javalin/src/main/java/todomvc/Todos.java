package todomvc;

import io.javalin.http.Context;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;


class Todos {

    private static final Map<UUID, Todo> todos = new ConcurrentHashMap<>();
    private static final AtomicBoolean hidden = new AtomicBoolean(false);

    public static Map<String, ? extends Object> asMap() {

        return Map.of( // NOSONAR
                "hidden", hidden.get(), // NOSONAR
                "active", todos.values().stream().filter(Todo::isActive).toList(), // NOSONAR
                "completed", todos.values().stream().filter(Todo::isCompleted).toList(), // NOSONAR
                "activeCount", todos.values().stream().filter(Todo::isActive).count(), // NOSONAR
                "completedCount", todos.values().stream().filter(Todo::isCompleted).count());
    }


    public static void handleTodos(Context ctx) {

        if (hasParam(ctx, "todo")) {
            addNewTodo(ctx);
        }

        ctx.redirect("/");
    }


    public static void handleTodo(Context ctx) {

        if (hasParam(ctx, "complete")) {
            markAsCompleted(ctx);
        } else if (hasParam(ctx, "revert")) {
            markAsActive(ctx);
        } else if (hasParam(ctx, "delete")) {
            clearTodo(ctx);
        }

        ctx.redirect("/");
    }


    public static void handleControls(Context ctx) {

        if (hasParam(ctx, "clear")) {
            clearCompleted(ctx);
        } else if (hasParam(ctx, "hide")) {
            hideCompleted();
        } else if (hasParam(ctx, "show")) {
            showCompleted();
        }

        ctx.redirect("/");
    }


    static void showCompleted() {

        hidden.set(false);
    }


    static void hideCompleted() {

        hidden.set(true);
    }


    private static boolean hasParam(Context ctx, String key) {

        return ctx.formParamMap().containsKey(key);
    }


    static void clearCompleted(Context ctx) {

        todos.entrySet().removeIf(e -> e.getValue().isCompleted());
    }


    static void markAsCompleted(Context ctx) {

        updateWith(ctx.formParam("complete"), Todo::markAsCompleted);
    }


    static void markAsActive(Context ctx) {

        updateWith(ctx.formParam("revert"), Todo::markAsActive);
    }


    static void clearTodo(Context ctx) {

        String id = ctx.formParam("delete");
        todos.entrySet().removeIf(e -> e.getKey().toString().equals(id));
    }


    static void addNewTodo(Context ctx) {

        String text = ctx.formParam("todo");
        Todo todo = Todo.createNew(text);
        todos.put(todo.id, todo);
    }


    private static void updateWith(String id, UnaryOperator<Todo> mapper) {

        Optional.ofNullable(todos.get(UUID.fromString(id))).map(mapper).ifPresent(todo -> {
            var copy = todo.copy();
            todos.put(copy.getUUID(), copy);
        });
    }
}
