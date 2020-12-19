package com.studiomediatech.component;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.Test;

public class ItemsLeftCountLabelTest {

  @Test
  public void testItemsLeftCountLabelCaptions() {

    WicketTester tester = new WicketTester();

    IModel<Integer> countModel = new Model<Integer>();
    String id = "id";
    ItemsLeftCountLabel label = new ItemsLeftCountLabel(id, countModel);

    countModel.setObject(0);
    tester.startComponentInPage(label);
    tester.assertLabel(id, "items left");

    countModel.setObject(1);
    tester.startComponentInPage(label);
    tester.assertLabel(id, "item left");

    countModel.setObject(3);
    tester.startComponentInPage(label);
    tester.assertLabel(id, "items left");
  }
}
