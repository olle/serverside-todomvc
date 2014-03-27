package com.studiomediatech;

import java.util.List;

import com.studiomediatech.component.TodoPanel;
import com.studiomediatech.domain.Todo;
import com.studiomediatech.domain.Todo.Status;
import com.studiomediatech.model.TodoListModel;
import com.studiomediatech.model.TodoModel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

public class HomePage extends BasePage {

  private static final long serialVersionUID = -8474930871026680132L;

  // All the state are encapsulated in models
  private final IModel<List<Todo>> todos;
  private final IModel<Todo> todo;
  private final IModel<Integer> allTodosCountModel;
  private final IModel<Boolean> isTodosEmptyModel;

  public HomePage() {

    super();

    this.todos = new TodoListModel();
    this.allTodosCountModel = new PropertyModel(this.todos, "size");
    this.isTodosEmptyModel = new PropertyModel(this.todos, "isEmpty");
    this.todo = new TodoModel();

    add(createNewTodoForm());
    Component container = createMainTodosContainer();

    container.add(createTodoList());

    add(container);

    PropertyModel<List<Todo>> allTodosModel = new PropertyModel<List<Todo>>(this, "allTodos");

    ListView<Todo> todos;

    main.add(todos = );

    final WebMarkupContainer footer;

    add(footer = new WebMarkupContainer("footer", this.allTodosCountModel) {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onConfigure() {
        super.onConfigure();
        Integer size = (Integer) getDefaultModelObject();
        setVisible(size != null && size > 0);
      }
    });

    PropertyModel<Integer> todosCountModel = new PropertyModel<Integer>(this, "todos.size");

    footer.add(new Label("count", todosCountModel));
    footer.add(new Label("countText", new StringResourceModel("todos.count.remaining",
                                                              (IModel<?>) null,
                                                              todosCountModel)));

    final WebMarkupContainer clear = new WebMarkupContainer("clearCompleted");

    clear.add(new AjaxEventBehavior("click") {
      private static final long serialVersionUID = 1L;

      @Override
      protected void onEvent(AjaxRequestTarget target) {
        TodoMVC.getStorage(getSession()).delete(getCompleted());
        setResponsePage(findPage());
      }
    });

    PropertyModel<Integer> completedCountModel = new PropertyModel<Integer>(this, "completed.size");

    clear.add(new Label("completed", completedCountModel));

    footer.add(clear);
  }

  private Behavior createTodoList() {

    ListView<Todo> list = new ListView<Todo>("todos", this.todos) {
      private static final long serialVersionUID = 1L;

      @Override
      protected void populateItem(final ListItem<Todo> item) {

        IModel<Todo> model = item.getModel();

        item.add(new TodoPanel("entry", model));
        item.add(new AttributeAppender("class", new PropertyModel<String>(model, "status")));

        TextField<String> editTodoText;

        if (HomePage.this.todo.getStatus() == Status.EDITING) {
          editTodoText.add(new AttributeModifier("autofocus", Model.of("autofocus")));
        }

        Label label;

        item.add(label = new Label("todo", model));

        if (HomePage.this.todo.getStatus() == Status.ACTIVE) {
          label.add(new AjaxEventBehavior("dblclick") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onEvent(AjaxRequestTarget target) {
              HomePage.this.todo.setStatus(Status.EDITING);
              setResponsePage(findPage());
            }
          });
        }

        item.add(new AjaxCheckBox("toggle", new PropertyModel<Boolean>(model, "completed")) {
          private static final long serialVersionUID = 1L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            TodoMVC.getStorage(getSession()).save(HomePage.this.todo);
            setResponsePage(findPage());
          }
        });

        item.add(new WebMarkupContainer("destroy").add(new AjaxEventBehavior("click") {
          private static final long serialVersionUID = 1L;

          @Override
          protected void onEvent(AjaxRequestTarget target) {
            setResponsePage(findPage());
          }
        }));
      }
    };

    return list;
  }

  private Component createNewTodoForm() {

    Form<Todo> form = new Form<Todo>("form", new CompoundPropertyModel<Todo>(this.todo)) {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onSubmit() {
        getModelObject().save();
        setResponsePage(new HomePage());
      }

    };

    form.add(new TextField<String>("todo").setRequired(true));

    return form;
  }

  private Component createMainTodosContainer() {
    WebMarkupContainer container = new WebMarkupContainer("main", this.isTodosEmptyModel) {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onConfigure() {
        super.onConfigure();
        setVisible(!(boolean) getDefaultModelObject());
      }

    };

    return container;
  }

}
