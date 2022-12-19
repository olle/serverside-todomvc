package com.studiomediatech.todomvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;


public class TodoMVC {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoMVC.class);

    private static final TodoService service = new TodoService();

    public static void main(String[] args) {

        Spark.staticFiles.location("/public");
        port(8080);

        handleIndexGET();
        handleTodosPOST();
        handleTodoPOST();
        handleControlsPOST();
    }


    private static void handleIndexGET() {

        get("/", (req, res) -> new ModelAndView(viewModel(), "index.mustache"), engine());
    }


    private static void handleTodosPOST() {

        post("/todos",
            (req, res) -> {
                LOGGER.warn("POST /todos with {}", req.queryParams());
                service.addNewTodo(req.queryParams("todo"));

                return redirect(res);
            });
    }


    private static void handleTodoPOST() {

        post("/todo",
            (req, res) -> {
                LOGGER.warn("POST /todo with {}", req.queryParams());

                handle(req).withParam("complete", service::completeTodoItem);
                handle(req).withParam("revert", service::activateTodoItem);
                handle(req).withParam("delete", service::deleteTodoItem);

                return redirect(res);
            });
    }


    private static void handleControlsPOST() {

        post("/controls",
            (req, res) -> {
                LOGGER.warn("POST /controls with {}", req.queryParams());
                handle(req).withParam("hide", service::hideCompleted);
                handle(req).withParam("show", service::showCompleted);

                return redirect(res);
            });
    }


    private static Object redirect(Response res) {

        res.redirect("/");

        return null;
    }


    private static Map<String, Object> viewModel() {

        Map<String, Object> viewModel = new HashMap<>();

        List<Todo> active = service.getActiveTodos();
        viewModel.put("active", active);
        viewModel.put("activeCount", active.size());

        List<Todo> completed = service.getCompletedTodos();
        viewModel.put("completed", completed);
        viewModel.put("completedCount", completed.size());

        viewModel.put("hidden", service.isHidden());

        return viewModel;
    }


    private static MustacheTemplateEngine engine() {

        return new MustacheTemplateEngine();
    }


    private static CaseHandler handle(Request req) {

        return new CaseHandler(req);
    }

    static final class CaseHandler {

        private final Request req;

        public CaseHandler(Request req) {

            this.req = req;
        }

        public void withParam(String key, Consumer<String> handler) {

            if (this.req.queryMap().hasKey(key)) {
                handler.accept(this.req.queryParams(key));
            }
        }
    }
}
