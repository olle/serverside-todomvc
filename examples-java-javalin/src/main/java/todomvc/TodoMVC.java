package todomvc;

import io.javalin.Javalin;

import io.javalin.rendering.template.JavalinJte;


public class TodoMVC {

    public static void main(String[] args) {

        JavalinJte.init();

        var app = Javalin
                .create(ctx -> ctx.staticFiles.add("/public"))
                .start(8080);

        app.get("/", ctx -> ctx.render("index.jte"));
    }
}
