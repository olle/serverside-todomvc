package controllers;

import javax.inject.Inject;
import javax.inject.Singleton;

import model.Todo;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import service.TodoService;

@Singleton
public class TodoMVCController {

  @Inject
  TodoService todoService;

  public Result index() {

    return Results.html().render("todos", todoService.list());
  }

  public Result addTodo(Context context, Todo todo) {

    todoService.create(todo);

    return Results.redirect("/");
  }
}
