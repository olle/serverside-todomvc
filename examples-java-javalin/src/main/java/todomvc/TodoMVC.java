package todomvc;

import io.javalin.Javalin;

import io.javalin.rendering.template.JavalinJte;


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
}
