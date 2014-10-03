package com.studiomediatech.todomvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.*;

import static spark.SparkBase.*;

public class TodoMVC {

  private static final String TEMPLATE = "index.mustache";

  private static final TodoService service = new TodoService();

  public static void main(String[] args) {
    staticFileLocation("/public");

    handleGetIndex();
    handlePostNewTodo();
    handleToggleStatus();
    handleDeleteTodo();
    handleEditTodo();
    handleUpdateTodo();
    handleMarkAllCompleted();
    handleClearAllCompleted();
  }

  private static void handleClearAllCompleted() {
    post("/clear-completed", (req, res) -> {
      service.clearCompleted();
      res.redirect("/");
      return null;
    });
  }

  private static void handleMarkAllCompleted() {
    post("/toggle-all-completed", (req, res) -> {
      service.markAllCompleted();
      res.redirect("/");
      return null;
    });
  }

  private static void handleUpdateTodo() {
    post("/update", (req, res) -> {
      service.updateTodo(req.queryParams("id"), req.queryParams("todo"));
      res.redirect("/");
      return null;
    });
  }

  private static void handleEditTodo() {
    get("/edit/:id", (req, res) -> {
      service.editTodo(req.params(":id"));
      return new ModelAndView(viewModel(), TEMPLATE);
    }, engine());
  }

  private static void handleGetIndex() {
    get("/", (req, res) -> {
      String filter = Optional.fromNullable(req.queryParams("filter")).or("all");
      service.setFilter(filter);
      return new ModelAndView(viewModel(), TEMPLATE);
    }, engine());
  }

  private static void handlePostNewTodo() {
    post("/new-todo", (req, res) -> {
      service.addNewTodo(req.queryParams("todo"));
      res.redirect("/");
      return null;
    });
  }

  private static void handleToggleStatus() {
    post("/toggle-status", (req, res) -> {
      service.toggleStatus(req.queryParams("id"));
      res.redirect("/");
      return null;
    });
  }

  private static void handleDeleteTodo() {
    post("/delete", (req, res) -> {
      service.deleteTodo(req.queryParams("id"));
      res.redirect("/");
      return null;
    });
  }

  private static Map<String, Object> viewModel() {

    Map<String, Object> viewModel = new HashMap<>();

    List<Todo> ts = service.getTodos();

    viewModel.put("hasTodos", service.hasTodos());
    viewModel.put("hasFilteredTodos", service.hasFilteredTodos());
    viewModel.put("todos", ts);
    viewModel.put("editing", ts.stream().anyMatch((t) -> t.isEditing()));

    Map<String, Object> filter = new HashMap<>();
    filter.put("name", service.getFilter());
    filter.put("all", service.isFilter("all"));
    filter.put("active", service.isFilter("active"));
    filter.put("completed", service.isFilter("completed"));
    viewModel.put("filter", filter);

    Map<String, Object> active = new HashMap<>();
    long count = ts.stream().filter((t) -> t.isActive()).count();
    active.put("count", count);
    active.put("label", count > 1 ? "items" : "item");
    viewModel.put("active", active);

    Map<String, Object> completed = new HashMap<>();
    completed.put("count", ts.stream().filter((t) -> t.isCompleted()).count());
    viewModel.put("completed", completed);

    return viewModel;
  }

  private static MustacheTemplateEngine engine() {
    return new MustacheTemplateEngine();
  }

}
