package com.studiomediatech;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
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
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    response.render(CssHeaderItem.forUrl("css/style.css"));
  }

}