package com.studiomediatech;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * An application that manages todos, or tasks.
 */
public class TodoMVC extends WebApplication {

  private static final TodoService todoService = new TodoService();

  public TodoMVC() {
    super();
  }

  @Override
  public Class<? extends WebPage> getHomePage() {
    return HomePage.class;
  }

  @Override
  public void init() {
    super.init();
    getMarkupSettings().setStripWicketTags(true);
  }

  public static TodoService getTodoService() {
    return TodoMVC.todoService;
  }
}
