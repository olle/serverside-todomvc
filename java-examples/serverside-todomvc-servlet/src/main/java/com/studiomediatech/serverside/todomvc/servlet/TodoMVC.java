package com.studiomediatech.serverside.todomvc.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studiomediatech.serverside.todomvc.common.storage.Storage;

public final class TodoMVC extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private final Storage<Todo, String> storage;

  public TodoMVC() {
    this.storage = Storage.<Todo, String> newGuavaCacheStorage();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println(req);
    String sessionId = req.getSession(true).getId();
    Iterable<Todo> todos = this.storage.forKey(sessionId).findAll();
    req.setAttribute("todos", todos);
    forwardToView(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                                                                         IOException {
    System.out.println(req.toString());
    forwardToView(req, resp);
  }

  private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                                                                              IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher("/home.jsp");
    dispatcher.forward(req, resp);
  }
}
