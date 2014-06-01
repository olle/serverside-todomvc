package com.studiomediatech;

import java.util.List;

import com.studiomediatech.domain.Todo;
import com.studiomediatech.model.TodoListModel;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
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
public class HomePage extends WebPage {

  private static final long serialVersionUID = -8474930871026680132L;

  // All extra state is encapsulated by models
  private final IModel<List<Todo>> todos;
  private final IModel<Integer> count;
  private final IModel<Todo> newTodo;
  private final IModel<Todo> editTodo;

  public HomePage() {

    super();

    // Models
    this.todos = new TodoListModel();
    this.count = new PropertyModel<>(this.todos, "size");
    this.newTodo = Model.of(new Todo());
    this.editTodo = Model.of();

    // Components
    add(newTodoForm());
    add(todoList());
    add(count());
  }

  private Component newTodoForm() {

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
        item.add(todoLink(item.getModel()));
      }

      private Component todoLink(IModel<Todo> model) {

        Link<Todo> link = new Link<Todo>("link", model) {

          private static final long serialVersionUID = 1L;

          @Override
          public void onClick() {
            HomePage.this.editTodo.setObject(getModelObject());
          }
        };

        link.add(new Label("text", new PropertyModel<>(model, "todo")));

        return link;
      }
    };

    return list;
  }

  private Component count() {
    return new Label("count", this.count);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    response.render(CssHeaderItem.forUrl("css/style.css"));
  }

}
