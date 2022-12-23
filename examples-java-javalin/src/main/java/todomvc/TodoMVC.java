package todomvc;

import io.javalin.Javalin;

import io.javalin.rendering.template.JavalinJte;


public class TodoMVC {

    public static void main(String[] args) {

        JavalinJte.init();

        var app = Javalin
                .create(config -> config.staticFiles.add("/public"))
                .start(8080);

        app.get("/", IndexPage::show);
        app.post("/todo", Todos::handleTodo);
        app.post("/todos", Todos::handleTodos);
        app.post("/todos/{id}", Todos::handleTodo);
        app.post("/controls", Todos::handleControls);
    }
}
