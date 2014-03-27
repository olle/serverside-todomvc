package com.studiomediatech;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * An application that manages todos, or tasks.
 */
public class TodoMVC extends WebApplication {

  static final String JS_BUCKET = "foot";

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
}
