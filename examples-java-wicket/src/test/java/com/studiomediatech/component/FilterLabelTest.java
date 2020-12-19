package com.studiomediatech.component;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.Test;

import com.studiomediatech.domain.Filter;
import com.studiomediatech.domain.Status;


public class FilterLabelTest {

  @Test
  public void testFilterLabelCaptions() {

    WicketTester tester = new WicketTester();
    IModel<Filter> filterModel = new Model<Filter>();
    String id = "id";
    FilterLabel label = new FilterLabel(id, filterModel);

    filterModel.setObject(null);
    tester.startComponentInPage(label);
    tester.assertLabel(id, "None");

    filterModel.setObject(new Filter(Status.ACTIVE));
    tester.startComponentInPage(label);
    tester.assertLabel(id, "Active");

    filterModel.setObject(new Filter(Status.COMPLETED));
    tester.startComponentInPage(label);
    tester.assertLabel(id, "Completed");

    filterModel.setObject(new Filter(Status.ACTIVE, Status.COMPLETED));
    tester.startComponentInPage(label);
    tester.assertLabel(id, "All");

    filterModel.setObject(new Filter(Status.COMPLETED, Status.ACTIVE));
    tester.startComponentInPage(label);
    tester.assertLabel(id, "All");
  }
}
