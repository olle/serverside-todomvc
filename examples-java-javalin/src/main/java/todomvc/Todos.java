package todomvc;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

import io.javalin.http.Context;

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