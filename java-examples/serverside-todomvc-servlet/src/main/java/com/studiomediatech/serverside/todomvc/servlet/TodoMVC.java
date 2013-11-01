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
import com.google.common.collect.Lists;
import com.studiomediatech.serverside.todomvc.common.storage.Repository;
import com.studiomediatech.serverside.todomvc.common.storage.Storage;

public final class TodoMVC extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private final Storage<Todo, Long> storage;

  public TodoMVC() {
    Todo initialTodo = new Todo("Implement TodoMVC in a new framework");
    List<Todo> defaults = Arrays.asList(initialTodo);
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
    String command = requestURI.substring(requestURI.lastIndexOf("/") + 1, requestURI.lastIndexOf("."));
    System.out.println("PARSED COMMAND: " + command);
    return command;
  }

  String dispatchControl(String command, HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Repository<Todo, Long> repository = getSessionRepository(req);
    if (command.equals("home")) {
      Iterable<Todo> todos = repository.findAll();
      System.out.println("FOUND " + todos);
      List<Todo> ts = Lists.newArrayList(Optional.fromNullable(todos).or(new ArrayList<Todo>()));
      req.setAttribute("todos", ts);
      return "home";
    }
    else if (command.equals("new")) {
      System.out.println(req);
      String newTodo = req.getParameter("new-todo");
      Todo todo = new Todo(newTodo);
      repository.save(todo);
      resp.sendRedirect("home.do");
      return "home";
    }
    else {
      System.err.println("COMMAND NOT KNOWN: " + command);;
      return "home";
    }
  }

  private Repository<Todo, Long> getSessionRepository(HttpServletRequest req) {
    String sessionId = req.getSession(true).getId();
    Repository<Todo, Long> repository = this.storage.forKey(sessionId);
    System.out.println("FETCHING SESSINO REPO FOR: " + sessionId + ", FOUND: " + repository);
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
