package com.studiomediatech;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.studiomediatech.serverside.todomvc.common.storage.Storage;

import org.apache.wicket.Session;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

public class TodoMVC extends WebApplication {

  public static final String JS_BUCKET = "foot";

  private final Storage<Todo> storage;

  public TodoMVC() {
    this.storage = new Storage<Todo>(new Callable<List<Todo>>() {
      @Override
      public List<Todo> call() throws Exception {
        ArrayList<Todo> todos = new ArrayList<Todo>();
        Todo todo = new Todo("Implement TodoMVC in a new framework");
        // TODO: Solve some other way!
        // todo.setId(Integer.toString(todo.hashCode()));
        todos.add(todo);
        return todos;
      }
    });
  }

  public static Storage<Todo> getStorage(Session session) {
    return ((TodoMVC) get()).getSessionStorage(session);
  }

  private Storage<Todo> getSessionStorage(Session session) {
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
