package todomvc;

import io.javalin.http.Context;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;


class Todos {

    private static final Map<UUID, Todo> todos = new ConcurrentHashMap<>();

    public static Map<String, ? extends Object> asMap() {

        return Map.of( // NOSONAR
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
        }

        ctx.redirect("/");
    }


    public static void handleControls(Context ctx) {

        if (hasParam(ctx, "clear")) {
            clearCompleted(ctx);
        }

        ctx.redirect("/");
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


    static void addNewTodo(Context ctx) {

        Todo todo = Todo.createNew(ctx.formParam("todo"));
        todos.put(todo.id, todo);
    }


    private static void updateWith(String id, UnaryOperator<Todo> mapper) {

        Optional.ofNullable(todos.get(UUID.fromString(id)))
            .map(mapper)
            .ifPresent(todo -> {
                var copy = todo.copy();
                todos.put(copy.getUUID(), copy);
            });
    }
}
