package com.studiomediatech;

import java.util.List;

import com.studiomediatech.domain.Filter;
import com.studiomediatech.domain.Status;
import com.studiomediatech.domain.Todo;
import com.studiomediatech.model.FilteredTodoListModel;
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
@SuppressWarnings("serial")
public class HomePage extends WebPage {

  private static final String TOGGLE_ALL_BUTTON_ID = "toggleAllButton";
  private static final String IS_ACTIVE_CLASS = "is-active";
  private static final String CLASS_ATTRIBUTE = "class";
  private static final String FILTER_COMPLETED_ID = "filterCompleted";
  private static final String FILTER_ACTIVE_ID = "filterActive";
  private static final String FILTER_ALL_ID = "filterAll";
  private static final String CLEAR_COMPLETED_ID = "clearCompleted";
  private static final String CONTROLS_ID = "controls";
  private static final String TODO_TEXT_ID = "text";
  private static final String EDIT_LINK_ID = "link";
  private static final String DELETE_ID = "delete";
  private static final String STATUS_CAPTION_ID = "invertedStatusCaption";
  private static final String TOGGLE_ICON_ID = "toggleButton";
  private static final String TOGGLE_COMPLETE_ID = "toggleComplete";
  private static final String TODO_LIST_ID = "todos";
  private static final String FILTER_CAPTION_ID = "filter";
  private static final String TOGGLE_ALL_ID = "completeAll";
  private static final String NEW_TODO_ID = "new";
  private static final String ACTIVE_COUNT_ID = "activeCount";
  private static final String ACTIVE_COUNT_CAPTION_ID = "activeCountCaption";

  private static final long serialVersionUID = -8474930871026680132L;

  // All state is encapsulated by models
  private final IModel<Filter> filter;
  private final IModel<List<Todo>> all;
  private final IModel<List<Todo>> active;
  private final IModel<List<Todo>> completed;
  private final IModel<List<Todo>> filtered;
  private final IModel<Todo> newTodo;
  private final IModel<Boolean> toggleAllCompleted;

  public HomePage() {
    super();

    // Models
    this.all = new TodoListModel();
    this.filter = Model.of(new Filter(Status.ACTIVE, Status.COMPLETED));
    this.active = new FilteredTodoListModel(this.all, Model.of(new Filter(Status.ACTIVE)));
    this.completed = new FilteredTodoListModel(this.all, Model.of(new Filter(Status.COMPLETED)));
    this.filtered = new FilteredTodoListModel(this.all, this.filter);
    this.newTodo = Model.of(new Todo());
    this.toggleAllCompleted = Model.of(true);

    // Components
    add(newTodo());
    add(toggleAll());
    add(filter());
    add(todoList());
    add(controls());
  }

  private Component newTodo() {
    Form<Todo> form = new Form<Todo>(NEW_TODO_ID, new CompoundPropertyModel<Todo>(this.newTodo)) {
      @Override
      protected void onSubmit() {
        getModelObject().save();
        setModelObject(new Todo());
        HomePage.this.all.detach();
      }
    };

    // NOTE: Property binding
    form.add(new TextField<String>("todo").setRequired(true));

    return form;
  }

  private Component toggleAll() {
    Form<Boolean> form = new Form<Boolean>(TOGGLE_ALL_ID, this.toggleAllCompleted) {
      @Override
      protected void onSubmit() {
        Boolean toCompletedStatus = getModelObject();
        TodoMVC.getTodoService().toggleAllCompleted(toCompletedStatus);
        setModelObject(!toCompletedStatus);
        HomePage.this.all.detach();
      }
    };

    form.add(new WebMarkupContainer(TOGGLE_ALL_BUTTON_ID).add(new AttributeAppender(CLASS_ATTRIBUTE,
                                                                                    new Model<String>() {
                                                                                      @Override
                                                                                      public String getObject() {
                                                                                        return HomePage.this.toggleAllCompleted.getObject()
                                                                                                                                           ? ""
                                                                                                                                           : IS_ACTIVE_CLASS;
                                                                                      }
                                                                                    }).setSeparator(" ")));

    return form;
  }

  private Component filter() {
    return new Label(FILTER_CAPTION_ID, this.filter);
  }

  private Component todoList() {
    ListView<Todo> list = new ListView<Todo>(TODO_LIST_ID, this.filtered) {
      @Override
      protected void onConfigure() {
        setVisible(HomePage.this.all.getObject().size() > 0);
      }

      @Override
      protected void populateItem(ListItem<Todo> item) {
        item.add(toggleComplete(item.getModel()));
        item.add(delete(item.getModel()));
        item.add(editLink(item.getModel()));
        item.add(new AttributeAppender(CLASS_ATTRIBUTE, new PropertyModel<String>(item.getModel(), "status")).setSeparator(" "));
      }

      private Component toggleComplete(final IModel<Todo> model) {
        Form<Todo> form = new Form<Todo>(TOGGLE_COMPLETE_ID, model) {
          @Override
          protected void onSubmit() {
            getModelObject().toggleComplete();
          }
        };

        form.add(toggleButton(model));

        return form;
      }

      private Component toggleButton(final IModel<Todo> model) {
        WebMarkupContainer button = new WebMarkupContainer(TOGGLE_ICON_ID);

        button.add(new AttributeAppender(CLASS_ATTRIBUTE, new Model<String>() {
          @Override
          public String getObject() {
            if (model.getObject().getStatus() == Status.COMPLETED) {
              return IS_ACTIVE_CLASS;
            }

            return "";
          }
        }).setSeparator(" "));

        button.add(new Label(STATUS_CAPTION_ID, new Model<String>() {
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
        return new Form<Todo>(DELETE_ID, model) {
          @Override
          protected void onSubmit() {
            getModelObject().delete();
            HomePage.this.all.detach();
          }
        };
      }

      private Component editLink(IModel<Todo> model) {
        Link<Todo> link = new Link<Todo>(EDIT_LINK_ID, model) {
          @Override
          public void onClick() {
            System.out.println("TODO: Set edit model: " + getDefaultModelObjectAsString());
          }
        };

        link.add(new Label(TODO_TEXT_ID, new PropertyModel<String>(model, "todo")));

        return link;
      }
    };

    return list;
  }

  private Component controls() {
    WebMarkupContainer controls = new WebMarkupContainer(CONTROLS_ID) {
      @Override
      protected void onConfigure() {
        setVisible(HomePage.this.all.getObject().size() > 0);
      }
    };

    controls.add(activeCount());
    controls.add(activeCountCaption());
    controls.add(clearCompleted());
    controls.add(filterAll());
    controls.add(filterActive());
    controls.add(filterCompleted());

    return controls;
  }

  private Component activeCount() {
    return new Label(ACTIVE_COUNT_ID, new PropertyModel<Integer>(this.active, "size"));
  }

  private Component activeCountCaption() {
    return new Label(ACTIVE_COUNT_CAPTION_ID,
                     new StringResourceModel("todos.count.active",
                                             HomePage.this,
                                             null,
                                             new Object[]{ new PropertyModel<Integer>(this.active, "size") }));
  }

  private Component clearCompleted() {
    Form<Void> form = new Form<Void>(CLEAR_COMPLETED_ID) {
      @Override
      protected void onConfigure() {
        setVisible(HomePage.this.completed.getObject().size() > 0);
      }

      @Override
      protected void onSubmit() {
        TodoMVC.getTodoService().clearCompleted();
        HomePage.this.all.detach();
      }
    };

    form.add(new Label("completedCount", new PropertyModel<Integer>(this.completed, "size")));

    return form;
  }

  private Component filterAll() {
    return filterLink(FILTER_ALL_ID, new Filter(Status.ACTIVE, Status.COMPLETED));
  }

  private Component filterActive() {
    return filterLink(FILTER_ACTIVE_ID, new Filter(Status.ACTIVE));
  }

  private Component filterCompleted() {
    return filterLink(FILTER_COMPLETED_ID, new Filter(Status.COMPLETED));
  }

  private Component filterLink(String id, final Filter filter) {
    Link<Void> link = new Link<Void>(id) {
      @Override
      public void onClick() {
        HomePage.this.filter.setObject(filter);
        setResponsePage(getPage());
      }
    };

    link.add(new AttributeAppender(CLASS_ATTRIBUTE, new Model<String>() {
      @Override
      public String getObject() {
        return HomePage.this.filter.getObject().equals(filter) ? IS_ACTIVE_CLASS : "";
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
