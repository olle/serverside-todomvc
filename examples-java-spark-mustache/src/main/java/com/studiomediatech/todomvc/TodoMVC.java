package com.studiomediatech.todomvc;

import spark.ModelAndView;
import spark.Spark;

import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;


public class TodoMVC {

    private static final String TEMPLATE = "index.mustache";

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
                if (req.params().containsKey("complete")) {
                    service.completeTodoItem(req.queryParams("complete"));
                }

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
}
