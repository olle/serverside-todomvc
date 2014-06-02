package com.studiomediatech;

import java.util.List;

import com.studiomediatech.domain.Filter;
import com.studiomediatech.domain.Status;
import com.studiomediatech.domain.Todo;
import com.studiomediatech.model.CompletedCountModel;
import com.studiomediatech.model.MoreThanZeroModel;
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

  private static final String ACTIVE_COUNT_ID = "activeCount";
  private static final String ACTIVE_COUNT_CAPTION_ID = "activeCountCaption";

  private static final long serialVersionUID = -8474930871026680132L;

  // All state is encapsulated by models
  private final IModel<Filter> filter;

  private final IModel<List<Todo>> allTodos;
  private final IModel<List<Todo>> completedTodos;
  private final IModel<List<Todo>> filteredTodos;

  private final IModel<List<Todo>> activeTodos;
  private final IModel<Integer> activeCount;

  private final IModel<Integer> completedCount;
  private final IModel<Todo> newTodo;
  private final IModel<Todo> editTodo;
  private final IModel<Boolean> hasFiltered;
  private final IModel<Boolean> hasCompleted;
  private final IModel<Boolean> hasTodos;

  public HomePage() {

    super();

    // Models
    this.filter = Model.of(new Filter(Status.ACTIVE, Status.COMPLETED));
    this.filteredTodos = new TodoListModel(this.filter);

    this.allTodos = new TodoListModel(Model.of(new Filter(Status.ACTIVE, Status.COMPLETED)));
    this.completedTodos = new TodoListModel(Model.of(new Filter(Status.COMPLETED)));

    this.hasFiltered = new MoreThanZeroModel(new PropertyModel<Integer>(this.filteredTodos, "size"));
    this.hasTodos = new MoreThanZeroModel(new PropertyModel<Integer>(this.allTodos, "size"));

    this.activeTodos = new TodoListModel(Model.of(new Filter(Status.ACTIVE)));
    this.activeCount = new PropertyModel<Integer>(this.activeTodos, "size");

    this.newTodo = Model.of(new Todo());
    this.editTodo = Model.of();
    this.completedCount = new CompletedCountModel(this.allTodos);
    this.hasCompleted = new MoreThanZeroModel(this.completedCount);

    // Components
    add(newTodo());
    add(completeAll());
    add(filter());
    add(todoList());
    add(activeCount());
    add(activeCountCaption());
    add(clearCompleted());
    add(filterAll());
    add(filterActive());
    add(filterCompleted());
  }

  @Override
  public void detachModels() {
    super.detachModels();
    this.filteredTodos.detach();
    this.activeTodos.detach();
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

  private Component filter() {
    return new Label("filter", new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return getString(HomePage.this.filter.getObject().toString());
      }
    });
  }

  private Component todoList() {

    ListView<Todo> list = new ListView<Todo>("todos", this.filteredTodos) {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onConfigure() {
        setVisible(HomePage.this.hasFiltered.getObject());
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

  private Component activeCount() {

    return new Label(ACTIVE_COUNT_ID, this.activeCount);
  }

  private Component activeCountCaption() {

    return new Label(ACTIVE_COUNT_CAPTION_ID, new StringResourceModel("todos.count.active",
                                                                      HomePage.this,
                                                                      null,
                                                                      new Object[]{ this.activeCount }));
  }

  private Component clearCompleted() {

    Form<Void> form = new Form<Void>("clearCompleted") {

      private static final long serialVersionUID = 1L;

      @Override
      protected void onConfigure() {
        setVisible(HomePage.this.hasCompleted.getObject());
      }

      @Override
      protected void onSubmit() {
        TodoMVC.getTodoService().clearCompleted();
      }
    };

    form.add(new Label("completedCount", this.completedCount));

    return form;
  }

  private Component filterAll() {
    return filterLink("filterAll", new Filter(Status.ACTIVE, Status.COMPLETED));
  }

  private Component filterActive() {
    return filterLink("filterActive", new Filter(Status.ACTIVE));
  }

  private Component filterCompleted() {
    return filterLink("filterCompleted", new Filter(Status.COMPLETED));
  }

  private Component filterLink(String id, final Filter filter) {
    Link<Void> link = new Link<Void>(id) {

      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        HomePage.this.filter.setObject(filter);
        setResponsePage(getPage());
      }
    };

    link.add(new AttributeAppender("class", new Model<String>() {

      private static final long serialVersionUID = 1L;

      @Override
      public String getObject() {
        return HomePage.this.filter.getObject().equals(filter) ? "is-active" : "";
      }
    }).setSeparator(" "));

    return link;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    response.render(CssHeaderItem.forUrl("css/style.css"));
  }
}
