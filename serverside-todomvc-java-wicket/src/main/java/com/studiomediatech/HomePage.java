package com.studiomediatech;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.studiomediatech.domain.Status;
import com.studiomediatech.domain.Todo;
import com.studiomediatech.model.TodoListModel;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

/**
 * The home page displays the list of todos and allows adding, deleting and
 * completing of the listed items.
 */
public class HomePage extends WebPage {

  private static final long serialVersionUID = -8474930871026680132L;

  // All extra state is encapsulated by models
  private final IModel<List<Todo>> todos;
  private final IModel<Integer> count;
  private final IModel<Todo> newTodo;
  private final IModel<Todo> editTodo;
  private final IModel<Integer> completedCount;

  public HomePage() {

    super();

    // Models
    this.todos = new TodoListModel();
    this.count = new PropertyModel<Integer>(this.todos, "size");
    this.newTodo = Model.of(new Todo());
    this.editTodo = Model.of();
    this.completedCount = new Model<Integer>() {

      private static final long serialVersionUID = 1L;

      @Override
      public Integer getObject() {

        return FluentIterable.<Todo> from(HomePage.this.todos.getObject()).filter(new Predicate<Todo>() {

          public boolean apply(Todo input) {
            return input.getStatus() == Status.COMPLETED;
          }
        }).size();
      }
    };

    // Components
    add(newTodo());
    add(completeAll());
    add(todoList());
    add(count());
    add(countCaption());
    add(clearCompleted());
  }

  private Component newTodo() {

    Form<Todo> form = new Form<Todo>("new", new CompoundPropertyModel<Todo>(this.newTodo)) {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onSubmit() {
        getModelObject().save();
        setModelObject(new Todo());
      }
    };

    form.add(new TextField<String>("todo").setRequired(true));

    return form;
  }

  private Component completeAll() {

    Form<Void> form = new Form<Void>("completeAll") {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onSubmit() {
        TodoMVC.getTodoService().markAllCompleted();
      }
    };

    return form;
  }

  private Component todoList() {

    ListView<Todo> list = new ListView<Todo>("todos", this.todos) {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onConfigure() {
        super.onConfigure();
        setVisible(getModelObject().size() > 0);
      }

      @Override
      protected void populateItem(ListItem<Todo> item) {
        item.add(toggleComplete(item.getModel()));
        item.add(delete(item.getModel()));
        item.add(todoLink(item.getModel()));
        item.add(new AttributeAppender("class", new PropertyModel<String>(item.getModel(), "status")).setSeparator(" "));
      }

      private Component toggleComplete(final IModel<Todo> model) {

        Form<Todo> form = new Form<Todo>("toggleComplete", model) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void onSubmit() {
            getModelObject().toggleComplete();
          }

        };

        form.add(toggleButton(model));

        return form;
      }

      private Component toggleButton(final IModel<Todo> model) {

        WebMarkupContainer button = new WebMarkupContainer("toggleButton");

        button.add(new AttributeAppender("class", new Model<String>() {

          private static final long serialVersionUID = 1L;

          @Override
          public String getObject() {
            if (model.getObject().getStatus() == Status.COMPLETED) {
              return "is-active";
            }

            return "";
          }
        }).setSeparator(" "));

        button.add(new Label("invertedStatusCaption", new Model<String>() {

          private static final long serialVersionUID = 1L;

          @Override
          public String getObject() {
            if (model.getObject().getStatus() == Status.COMPLETED) {
              return getString(Status.ACTIVE.name());
            }
            else {
              return getString(Status.COMPLETED.name());
            }
          }

        }));

        return button;
      }

      private Component delete(IModel<Todo> model) {

        Form<Todo> form = new Form<Todo>("delete", model) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void onSubmit() {
            getModelObject().delete();
            HomePage.this.todos.detach();
          }
        };

        return form;
      }

      private Component todoLink(IModel<Todo> model) {

        Link<Todo> link = new Link<Todo>("link", model) {

          private static final long serialVersionUID = 1L;

          @Override
          public void onClick() {
            HomePage.this.editTodo.setObject(getModelObject());
          }
        };

        link.add(new Label("text", new PropertyModel<String>(model, "todo")));

        return link;
      }
    };

    return list;
  }

  private Component count() {

    return new Label("count", this.count);
  }

  private Component countCaption() {

    return new Label("caption", new StringResourceModel("todos.count.remaining",
                                                        HomePage.this,
                                                        null,
                                                        new Object[]{ this.count }));
  }

  private Component clearCompleted() {

    Form<Void> form = new Form<Void>("clearCompleted") {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onSubmit() {
        TodoMVC.getTodoService().clearCompleted();
        HomePage.this.todos.detach();
      }
    };

    form.add(new Label("completedCount", this.completedCount));

    return form;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    response.render(CssHeaderItem.forUrl("css/style.css"));
  }

}
