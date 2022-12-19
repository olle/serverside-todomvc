package com.studiomediatech.todomvc;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;


public class TodoMVC {

    private static final String TEMPLATE_NAME = "index.mustache";

    private static final TodoService service = new TodoService();

    public static void main(String[] args) {

        Spark.staticFiles.location("/public");
        port(8080);

        mappingIndexGET();
        mappingTodosPOST();
        mappingTodosIdPOST();
        mappingTodoPOST();
        mappingControlsPOST();
    }


    static void mappingIndexGET() {

        get("/", (req, res) -> new ModelAndView(viewModel(), TEMPLATE_NAME), engine());
    }


    static void mappingTodosPOST() {

        post("/todos", (req, res) -> {
                service.addNewTodo(req.queryParams("todo"));

                return redirectRoot(res);
            });
    }


    static void mappingTodosIdPOST() {

        post("/todos/:id",
            (req, res) -> {
                handle(req).withParams("id", "update", service::updateTodo);

                return redirectRoot(res);
            });
    }


    static void mappingTodoPOST() {

        post("/todo",
            (req, res) -> {
                handle(req).withParam("complete", service::completeTodoItem);
                handle(req).withParam("revert", service::activateTodoItem);
                handle(req).withParam("delete", service::deleteTodoItem);
                handle(req).withParam("edit", service::editTodo);

                return redirectRoot(res);
            });
    }


    static void mappingControlsPOST() {

        post("/controls",
            (req, res) -> {
                handle(req).withParam("hide", service::hideCompleted);
                handle(req).withParam("show", service::showCompleted);
                handle(req).withParam("clear", service::clearCompleted);

                return redirectRoot(res);
            });
    }


    private static Object redirectRoot(Response res) {

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
        viewModel.put("editing", service.isEditing());

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


        public void withParams(String first, String second, BiConsumer<String, String> biHandler) {

            if (this.req.queryMap().hasKey(first) && this.req.queryMap().hasKey(second)) {
                biHandler.accept(this.req.queryParams(first), this.req.queryParams(second));
            }
        }
    }
}
