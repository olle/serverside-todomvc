package com.studiomediatech.component;

import com.studiomediatech.domain.Filter;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

public class FilterLabel extends Label {

  private static final long serialVersionUID = 4816493931362032096L;

  public FilterLabel(String id, IModel<Filter> filterModel) {
    super(id, new StringResourceModel("filter.${toString}", filterModel, "None"));
  }
}
