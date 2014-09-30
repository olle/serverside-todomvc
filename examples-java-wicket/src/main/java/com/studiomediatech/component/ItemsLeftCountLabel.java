package com.studiomediatech.component;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

public class ItemsLeftCountLabel extends Label {

  private static final long serialVersionUID = 7931160381020669268L;

  public ItemsLeftCountLabel(String id, IModel<Integer> countModel) {
    super(id, new StringResourceModel("items.left", null, new Object[]{ countModel }));
  }
}
