package com.studiomediatech.serverside.todomvc.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.studiomediatech.serverside.todomvc.common.storage.Repository;
import com.studiomediatech.serverside.todomvc.common.storage.Storage;
import com.studiomediatech.serverside.todomvc.servlet.Todo.Status;

public final class TodoMVC extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private final Storage<Todo, Long> storage;

  public TodoMVC() {
    Todo todo = new Todo("Implement TodoMVC in a new framework");
    List<Todo> defaults = Arrays.asList(todo);
    this.storage = Storage.<Todo, Long> newGuavaCacheStorage(defaults);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    handleRequest(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                                                                         IOException {
    handleRequest(req, resp);
  }

  private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                                                                              IOException {
    String command = parseCommand(req);
    String view = dispatchControl(command, req, resp);
    forwardToView(view, req, resp);
  }

  String parseCommand(HttpServletRequest req) {
    String requestURI = req.getRequestURI();
    int lastSlashIdx = requestURI.lastIndexOf("/");
    int lastDotIdx = requestURI.lastIndexOf(".");
    return requestURI.substring(lastSlashIdx + 1, lastDotIdx);
  }

  String dispatchControl(String command, HttpServletRequest req, HttpServletResponse resp) throws IOException {

    final Repository<Todo, Long> repository = getSessionRepository(req);

    if (command.equals("home")) {

      Iterable<Todo> all = repository.findAll();
      Optional<Iterable<Todo>> maybeTodos = Optional.fromNullable(all);
      Iterable<Todo> todos = maybeTodos.or(new ArrayList<Todo>());

      Iterable<Todo> completed = Iterables.filter(todos, new Predicate<Todo>() {
        @Override
        public boolean apply(Todo todo) {
          return todo.getStatus() == Status.COMPLETED;
        }
      });

      req.setAttribute("todos", Lists.newArrayList(todos));
      req.setAttribute("completed", Lists.newArrayList(completed));
      return "home";
    }
    else if (command.equals("new")) {

      String newTodo = req.getParameter("new-todo");
      Todo todo = new Todo(newTodo);
      repository.save(todo);
      resp.sendRedirect("home.do");
      return "";
    }
    else if (command.equals("toggle")) {

      long todoId = Long.parseLong(req.getParameter("todo-id"));
      Todo todo = repository.findOne(todoId);
      if (todo.getStatus() == Status.ACTIVE) {
        repository.save(new Todo(todo, Status.COMPLETED));
      }
      else {
        repository.save(new Todo(todo, Status.ACTIVE));
      }
      resp.sendRedirect("home.do");
      return "";
    }
    else if (command.equals("delete")) {

      long todoId = Long.parseLong(req.getParameter("todo-id"));
      repository.delete(todoId);
      resp.sendRedirect("home.do");
      return "";
    }
    else if (command.equals("clear")) {

      Iterable<Todo> todos = repository.findAll();
      for (Todo todo : todos) {
        if (todo.getStatus() == Status.COMPLETED) {
          repository.delete(todo.getId());
        }
      }
      resp.sendRedirect("home.do");
      return "";
    }
    else {

      return "home";
    }
  }

  private Repository<Todo, Long> getSessionRepository(HttpServletRequest req) {
    String sessionId = req.getSession(true).getId();
    Repository<Todo, Long> repository = this.storage.forKey(sessionId);
    return repository;
  }

  void forwardToView(String view, HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                                                                                   IOException {
    if (!resp.isCommitted()) {
      String path = "/" + view + ".jsp";
      RequestDispatcher dispatcher = req.getRequestDispatcher(path);
      dispatcher.forward(req, resp);
    }
  }
}
