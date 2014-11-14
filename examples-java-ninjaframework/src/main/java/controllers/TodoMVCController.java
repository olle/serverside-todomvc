package controllers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import model.Todo;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.session.Session;
import service.TodoService;

@Singleton
public class TodoMVCController {

  @Inject
  TodoService todoService;

  @FilterWith(TodoFilter.class)
  public Result index(Session session) {

    Result result = Results.html();

    String filter = session.get("filter");
    List<Todo> list = todoService.list(filter);
    result.render("todos", list);

    return result;
  }

  public Result addTodo(Context context, Todo todo) {

    todoService.create(todo);

    return Results.redirect("/");
  }
}
