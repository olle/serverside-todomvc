package com.studiomediatech;

import com.studiomediatech.serverside.todomvc.common.storage.Repository;
import com.studiomediatech.serverside.todomvc.common.storage.Storage;

import org.apache.wicket.Session;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

public class TodoMVC extends WebApplication {

  public static final String JS_BUCKET = "foot";

  private final Storage<Todo, String> storage;

  public TodoMVC() {
    this.storage = Storage.newGuavaCacheStorage();

    // TODO: Provide a way to pass default values to storage.
    // ArrayList<Todo> todos = new ArrayList<Todo>();
    // Todo todo = new Todo("Implement TodoMVC in a new framework");
    // todos.add(todo);
  }

  public static Repository<Todo, String> getStorage(Session session) {
    return ((TodoMVC) get()).getSessionStorage(session);
  }

  private Repository<Todo, String> getSessionStorage(Session session) {
    return this.storage.forKey(session.getId());
  }

  @Override
  public Class<? extends WebPage> getHomePage() {
    return Home.class;
  }

  @Override
  public void init() {
    super.init();

    setHeaderResponseDecorator(new IHeaderResponseDecorator() {
      @Override
      public IHeaderResponse decorate(IHeaderResponse response) {
        return new JavaScriptFilteredIntoFooterHeaderResponse(response, JS_BUCKET);
      }
    });

    getMarkupSettings().setStripWicketTags(true);
  }
}
