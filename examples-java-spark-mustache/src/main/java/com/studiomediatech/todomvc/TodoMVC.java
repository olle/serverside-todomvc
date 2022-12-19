package com.studiomediatech.todomvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.ModelAndView;
import spark.Request;
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

    private static final String TEMPLATE = "index.mustache";
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoMVC.class);

    private static final TodoService service = new TodoService();

    public static void main(String[] args) {

        Spark.staticFiles.location("/public");
        port(8080);

        handleIndexGET();
        handleTodosPOST();
        handleTodoPOST();
    }


    private static void handleIndexGET() {

        get("/", (req, res) -> new ModelAndView(viewModel(), TEMPLATE), engine());
    }


    private static void handleTodosPOST() {

        post("/todos", (req, res) -> {
                service.addNewTodo(req.queryParams("todo"));
                res.redirect("/");

                return null;
            });
    }


    private static void handleTodoPOST() {

        post("/todo",
            (req, res) -> {
                LOGGER.warn("POST with {}", req.queryParams());

                with(req).caseOf("complete", service::completeTodoItem);
                with(req).caseOf("revert", service::activateTodoItem);

                res.redirect("/");

                return null;
            });
    }


    private static Map<String, Object> viewModel() {

        Map<String, Object> viewModel = new HashMap<>();

        List<Todo> active = service.getActiveTodos();
        viewModel.put("active", active);
        viewModel.put("activeCount", active.size());

        List<Todo> completed = service.getCompletedTodos();
        viewModel.put("completed", completed);
        viewModel.put("completedCount", completed.size());

        return viewModel;
    }


    private static MustacheTemplateEngine engine() {

        return new MustacheTemplateEngine();
    }


    private static CaseHandler with(Request req) {

        return new CaseHandler(req);
    }

    static final class CaseHandler {

        private final Request req;

        public CaseHandler(Request req) {

            this.req = req;
        }

        public void caseOf(String key, Consumer<String> handler) {

            if (this.req.queryMap().hasKey(key)) {
                handler.accept(this.req.queryParams(key));
            }
        }
    }
}
