package todomvc;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;


public class TodoMVC {

    public static void main(String[] args) {

        var templateEngine = TemplateEngine.create(new ResourceCodeResolver(""), ContentType.Html);

        var app = Javalin
                .create(config -> {
                    config.staticFiles.add("/public");
                    config.fileRenderer(new JavalinJte(templateEngine));

                    config.routes.get("/", IndexPage::show);
                    config.routes.post("/todo", Todos::handleTodo);
                    config.routes.post("/todos", Todos::handleTodos);
                    config.routes.post("/todos/{id}", Todos::handleTodo);
                    config.routes.post("/controls", Todos::handleControls);
                })
                .start(8080);
    }
}
