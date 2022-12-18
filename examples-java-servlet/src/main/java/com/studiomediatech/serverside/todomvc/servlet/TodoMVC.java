package com.studiomediatech.serverside.todomvc.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.studiomediatech.serverside.todomvc.common.storage.Repository;
import com.studiomediatech.serverside.todomvc.common.storage.SimpleHashMapStorage;
import com.studiomediatech.serverside.todomvc.common.storage.Storage;
import com.studiomediatech.serverside.todomvc.servlet.Todo.Status;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class TodoMVC extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private final Storage<Todo, Long> storage = new SimpleHashMapStorage();



//  private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
//                                                                              IOException {
//    String command = parseCommand(req);
//    String view = dispatchControl(command, req, resp);
//    forwardToView(view, req, resp);
//  }

  String parseCommand(HttpServletRequest req) {
    String requestURI = req.getRequestURI();
    int lastSlashIdx = requestURI.lastIndexOf("/");
    int lastDotIdx = requestURI.lastIndexOf(".");
    return requestURI.substring(lastSlashIdx + 1, lastDotIdx);
  }

  String dispatchControl(String command, HttpServletRequest req, HttpServletResponse resp) throws IOException {

    final Repository<Todo, Long> repository = getSessionRepository(req);

    if (command.equals("home")) {

      Collection<Todo> all = repository.findAll();
      List<Todo> completed = all.stream().filter(todo -> todo.getStatus() == Status.COMPLETED).toList();
      
      req.setAttribute("todos", all);
      req.setAttribute("completed", completed);
      return "home";
    }    else if (command.equals("new")) {

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
    return this.storage.forKey(req.getSession(true).getId());
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
