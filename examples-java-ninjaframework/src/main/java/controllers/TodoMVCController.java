package controllers;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import model.Todo;

import com.google.common.base.Strings;

import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.session.Session;
import service.TodoService;

@Singleton
public class TodoMVCController {

  @Inject
  TodoService todoService;

  @FilterWith({ TodoFilter.class, EditFilter.class })
  public Result index(Session session) {

    Result result = Results.html();

    String filter = session.get("filter");

    boolean hasTodos = this.todoService.count() > 0;
    int activeCount = this.todoService.list("active").size();
    int completedCount = this.todoService.list("completed").size();
    List<Todo> todos = this.todoService.list(filter);
    boolean allCompleted = completedCount == this.todoService.count();

    result.render("hasTodos", hasTodos);
    result.render("activeCount", activeCount);
    result.render("completedCount", completedCount);
    result.render("todos", todos);
    result.render("allCompleted", allCompleted);

    return result;
  }

  public Result createOrUpdateTodo(Context context, Todo todo) {

    if (Strings.isNullOrEmpty(todo.getId())) {
      this.todoService.create(todo);
    }
    else {
      this.todoService.update(todo);
    }

    return Results.redirect("/");
  }

  public Result deleteTodo(@PathParam("id") String id) {

    this.todoService.delete(id);

    return Results.redirect("/");
  }

  public Result toggleTodoStatus(@PathParam("id") String id) {

    Todo todo = this.todoService.find(id);
    todo.toggleStatus();
    this.todoService.update(todo);

    return Results.redirect("/");
  }

  public Result clearCompleted() {

    Iterator<Todo> it = this.todoService.list("completed").iterator();

    while (it.hasNext()) {
      this.todoService.delete(it.next().getId());
    }

    return Results.redirect("/");
  }

  public Result markCompleted() {

    Iterator<Todo> it = this.todoService.list("active").iterator();

    while (it.hasNext()) {
      Todo todo = it.next();
      todo.toggleStatus();
      this.todoService.update(todo);
    }

    return Results.redirect("/");
  }
}
