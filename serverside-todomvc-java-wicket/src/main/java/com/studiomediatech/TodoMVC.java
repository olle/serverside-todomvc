package com.studiomediatech;

import com.studiomediatech.serverside.todomvc.common.storage.Repository;
import com.studiomediatech.serverside.todomvc.common.storage.Storage;

import org.apache.wicket.Session;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * An application that manages todos, or tasks.
 */
public class TodoMVC extends WebApplication {

  static final String JS_BUCKET = "foot";
  private final Storage<Todo, String> storage;

  /**
   * Creates a new TodoMVC web application and a storage with an initial default
   * todo item.
   */
  public TodoMVC() {
    super();
    this.storage = Storage.newGuavaCacheStorage(new Todo("Implement TodoMVC in a new framework"));
  }

  @Override
  public Class<? extends WebPage> getHomePage() {
    return HomePage.class;
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

  /**
   * Convenience method for access to the session storage repository.
   */
  public static Repository<Todo, String> getStorage(Session session) {
    return ((TodoMVC) get()).getSessionStorage(session);
  }

  /**
   * Returns a storage for the given session.
   */
  private Repository<Todo, String> getSessionStorage(Session session) {
    if (session.isTemporary()) {
      session.bind();
    }
    return this.storage.forKey(session.getId());
  }
}
