package com.studiomediatech;

import java.util.List;

import com.studiomediatech.component.FilterLabel;
import com.studiomediatech.component.ItemsLeftCountLabel;
import com.studiomediatech.component.TodoTextLabel;
import com.studiomediatech.domain.Filter;
import com.studiomediatech.domain.Status;
import com.studiomediatech.domain.Todo;
import com.studiomediatech.model.FilteredTodoListModel;
import com.studiomediatech.model.TodoListModel;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
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

/**
 * The home page displays the list of todos and allows adding, deleting and
 * completing of the listed items.
 */
@SuppressWarnings("serial")
public class HomePage extends WebPage {

  private static final Filter ALL = new Filter(Status.ACTIVE, Status.COMPLETED);
  private static final Filter ACTIVE = new Filter(Status.ACTIVE);
  private static final Filter COMPLETED = new Filter(Status.COMPLETED);

  private static final String IS_ACTIVE_CLASS = "is-active";

  private static final String CLASS_ATTRIBUTE = "class";
  private static final String AUTOFOCUS_ATTRIBUTE = "autofocus";

  private static final String TOGGLE_ALL_BUTTON_ID = "toggleAllButton";
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
  private static final String COMPLETED_COUNT_ID = "completedCount";

  private static final long serialVersionUID = -8474930871026680132L;

  // All state is encapsulated by models
  private final IModel<Filter> filter;
  private final IModel<List<Todo>> all;
  private final IModel<List<Todo>> filtered;
  private final IModel<Todo> newTodo;
  private final IModel<Todo> editTodo;
  private final IModel<Boolean> toggleAllCompleted;

  private final IModel<Integer> activeCount;
  private final IModel<Integer> completedCount;

  public HomePage() {
    super();

    // Models
    this.all = new TodoListModel();
    this.filter = Model.of(ALL);
    FilteredTodoListModel active = new FilteredTodoListModel(this.all, Model.of(ACTIVE));
    this.activeCount = new PropertyModel<Integer>(active, "size");
    FilteredTodoListModel completed = new FilteredTodoListModel(this.all, Model.of(COMPLETED));
    this.completedCount = new PropertyModel<Integer>(completed, "size");
    this.filtered = new FilteredTodoListModel(this.all, this.filter);
    this.newTodo = Model.of(new Todo());
    this.editTodo = Model.of();
    this.toggleAllCompleted = Model.of(true);

    // Components
    add(newTodo());
    add(toggleAll());
    add(new FilterLabel(FILTER_CAPTION_ID, this.filter));
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
    TextField<String> todoField = new TextField<String>("todo") {
      @Override
      protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        if (HomePage.this.editTodo.getObject() != null) {
          tag.remove(AUTOFOCUS_ATTRIBUTE);
        }
      }
    };

    todoField.setRequired(true);

    form.add(todoField);

    return form;
  }

  private Component toggleAll() {
    Form<Boolean> form = new Form<Boolean>(TOGGLE_ALL_ID, this.toggleAllCompleted) {
      @Override
      protected void onSubmit() {
        Boolean toCompleted = getModelObject();
        TodoMVC.getTodoService().toggleAllCompleted(toCompleted);
        setModelObject(!toCompleted);
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

  private Component todoList() {

    ListView<Todo> list = new ListView<Todo>(TODO_LIST_ID, this.filtered) {
      @Override
      protected void onConfigure() {
        setVisible(HomePage.this.all.getObject().size() > 0);
      }

      @Override
      protected void populateItem(final ListItem<Todo> item) {

        IModel<Todo> model = item.getModel();

        final Behavior invisibleWhenEditing = new Behavior() {
          @Override
          public void onConfigure(Component component) {
            component.setVisible(!item.getModelObject().equals(HomePage.this.editTodo.getObject()));
          }
        };

        item.add(toggleComplete(model).add(invisibleWhenEditing));
        item.add(delete(model).add(invisibleWhenEditing));
        item.add(edit(model).add(invisibleWhenEditing));
        item.add(editTodo(model));

        item.add(new AttributeAppender(CLASS_ATTRIBUTE, new Model<String>() {
          @Override
          public String getObject() {
            if (item.getModelObject().equals(HomePage.this.editTodo.getObject())) {
              return Status.EDITING.toString();
            }
            return item.getModelObject().getStatus().toString();
          }
        }).setSeparator(" "));
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

      private Component edit(IModel<Todo> model) {
        Link<Todo> link = new Link<Todo>(EDIT_LINK_ID, model) {
          @Override
          public void onClick() {
            HomePage.this.editTodo.setObject(getModelObject());
          }
        };
        link.add(new TodoTextLabel(TODO_TEXT_ID, model));
        return link;
      }

      private Component editTodo(IModel<Todo> model) {
        Form<Todo> form = new Form<Todo>("editTodo", new CompoundPropertyModel<Todo>(model)) {
          @Override
          protected void onSubmit() {
            HomePage.this.editTodo.setObject(null);
            HomePage.this.all.detach();
          }

          @Override
          protected void onConfigure() {
            setVisible(getModelObject().equals(HomePage.this.editTodo.getObject()));
          }
        };

        form.add(new TextField<String>("todo").setRequired(true));

        return form;
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

    controls.add(new Label(ACTIVE_COUNT_ID, this.activeCount));
    controls.add(new ItemsLeftCountLabel(ACTIVE_COUNT_CAPTION_ID, this.activeCount));

    controls.add(clearCompleted());
    controls.add(filterAll());
    controls.add(filterActive());
    controls.add(filterCompleted());

    return controls;
  }

  private Component clearCompleted() {
    Form<Void> form = new Form<Void>(CLEAR_COMPLETED_ID) {
      @Override
      protected void onConfigure() {
        setVisible(HomePage.this.completedCount.getObject() > 0);
      }

      @Override
      protected void onSubmit() {
        TodoMVC.getTodoService().clearCompleted();
        HomePage.this.toggleAllCompleted.setObject(true);
        HomePage.this.editTodo.setObject(null);
        HomePage.this.all.detach();
      }
    };

    form.add(new Label(COMPLETED_COUNT_ID, this.completedCount));

    return form;
  }

  private Component filterAll() {
    return filterLink(FILTER_ALL_ID, ALL);
  }

  private Component filterActive() {
    return filterLink(FILTER_ACTIVE_ID, ACTIVE);
  }

  private Component filterCompleted() {
    return filterLink(FILTER_COMPLETED_ID, COMPLETED);
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
