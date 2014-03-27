package com.studiomediatech.component;

import com.studiomediatech.domain.Todo;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class TodoPanel extends GenericPanel<Todo> {

  private static final long serialVersionUID = -131827637128512004L;

  private final IModel<Boolean> isEditingModel;

  public TodoPanel(String id, IModel<Todo> model) {

    super(id, new CompoundPropertyModel<>(model));

    this.isEditingModel = new PropertyModel<>(model, "isEditing");

    add(createTodoForm());
  }

  private Component createTodoForm() {

    Form<Todo> form = new Form<Todo>("todoForm", getModel()) {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onSubmit() {

        super.onSubmit();

        Todo todo = getModelObject();
        todo.setActive();
        todo.save();

        setResponsePage(findPage());
      }

    };

    form.add(new TextField<String>("todo") {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onConfigure() {

        super.onConfigure();

        setVisible(TodoPanel.this.isEditingModel.getObject());
      }

    });

    return form;
  }
}
