package com.studiomediatech;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.WebPage;

/**
 * A typical, idiomatic web application abstract base page, used to configure
 * general aspects of all pages in this app, using HTML-inheritance.
 * 
 * @author Olle Törnström - olle@studiomediatech.com
 */
public class BasePage extends WebPage {

  private static final long serialVersionUID = 5368687406332598957L;

  public BasePage() {
    super();
    add(createJavaScriptBucketContainer());
  }

  /**
   * JavaScript includes are gathered into a container at the bottom of the
   * page.
   */
  private Component createJavaScriptBucketContainer() {
    return new HeaderResponseContainer(TodoMVC.JS_BUCKET, TodoMVC.JS_BUCKET);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    response.render(CssHeaderItem.forUrl("css/style.css"));
  }

}