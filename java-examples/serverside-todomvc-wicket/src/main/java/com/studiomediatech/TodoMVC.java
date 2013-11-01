package com.studiomediatech;

import java.util.Arrays;
import java.util.List;

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
    Todo intialTodo = new Todo("Implement TodoMVC in a new framework");
    List<Todo> defaults = Arrays.asList(intialTodo);
    this.storage = Storage.newGuavaCacheStorage(defaults);
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
