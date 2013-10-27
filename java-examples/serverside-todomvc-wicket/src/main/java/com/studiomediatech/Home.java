package com.studiomediatech;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
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

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.studiomediatech.Todo.Status;

public class Home extends WebPage {
    private static final long serialVersionUID = 1L;

    public Home() {

        super();

        getSession().bind();

        add(new HeaderResponseContainer(TodoMVC.JS_BUCKET, TodoMVC.JS_BUCKET));

        final Form<Todo> form;

        add(form = new Form<Todo>("form", new CompoundPropertyModel<Todo>(new Todo())) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {
                super.onSubmit();
                TodoMVC.getStorage(getSession()).save(getModelObject());
                setModelObject(new Todo());
                setResponsePage(new Home());
            }
        });

        final TextField<String> newTodoText;

        form.add(newTodoText = new TextField<String>("todo"));

        newTodoText.setRequired(true);

        final WebMarkupContainer main;

        PropertyModel<Integer> allTodosCountModel = new PropertyModel<Integer>(this,
                "allTodos.size");

        add(main = new WebMarkupContainer("main", allTodosCountModel) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                Integer size = (Integer) getDefaultModelObject();
                setVisible(size != null && size > 0);
            }
        });

        main.add(new WebMarkupContainer("toggleAll").add(new AjaxEventBehavior("change") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onEvent(AjaxRequestTarget target) {
                System.out.println("TOGGLE ALL!" + getEvent());
            }
        }));

        PropertyModel<List<Todo>> allTodosModel = new PropertyModel<List<Todo>>(this, "allTodos");

        ListView<Todo> todos;

        main.add(todos = new ListView<Todo>("todos", allTodosModel) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final ListItem<Todo> item) {

                IModel<Todo> model = item.getModel();

                final Todo todo = item.getModelObject();

                item.add(new AttributeAppender("class", new PropertyModel<String>(model, "status")));

                Form<Todo> todoForm;

                item.add(todoForm = new Form<Todo>("todoForm", new CompoundPropertyModel<Todo>(
                        model)) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onSubmit() {
                        super.onSubmit();
                        Todo editedTodo = getModelObject();
                        editedTodo.setStatus(Status.ACTIVE);
                        TodoMVC.getStorage(getSession()).save(editedTodo);
                        setResponsePage(findPage());
                    }
                });

                TextField<String> editTodoText;

                todoForm.add(editTodoText = new TextField<String>("todo") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onConfigure() {
                        super.onConfigure();
                        setVisible(todo.getStatus() == Status.EDITING);
                    }
                });

                if (todo.getStatus() == Status.EDITING) {
                    editTodoText.add(new AttributeModifier("autofocus", Model.of("autofocus")));
                }

                Label label;

                item.add(label = new Label("todo", model));

                if (todo.getStatus() == Status.ACTIVE) {
                    label.add(new AjaxEventBehavior("dblclick") {
                        private static final long serialVersionUID = 1L;

                        @Override
                        protected void onEvent(AjaxRequestTarget target) {
                            todo.setStatus(Status.EDITING);
                            setResponsePage(findPage());
                        }
                    });
                }

                item.add(new AjaxCheckBox("toggle", new PropertyModel<Boolean>(model, "completed")) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        TodoMVC.getStorage(getSession()).save(todo);
                        setResponsePage(findPage());
                    }
                });

                item.add(new WebMarkupContainer("destroy").add(new AjaxEventBehavior("click") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onEvent(AjaxRequestTarget target) {
                        TodoMVC.getStorage(getSession()).delete(todo);
                        setResponsePage(findPage());
                    }
                }));
            }
        });

        final WebMarkupContainer footer;

        add(footer = new WebMarkupContainer("footer", allTodosCountModel) {
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
                (IModel<?>) null, todosCountModel)));

        final WebMarkupContainer clear = new WebMarkupContainer("clearCompleted");

        clear.add(new AjaxEventBehavior("click") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onEvent(AjaxRequestTarget target) {
                TodoMVC.getStorage(getSession()).delete(getCompleted());
                setResponsePage(findPage());
            }
        });

        PropertyModel<Integer> completedCountModel = new PropertyModel<Integer>(this,
                "completed.size");

        clear.add(new Label("completed", completedCountModel));

        footer.add(clear);
    }

    /**
     * @return Todo list for this session.
     */
    public Collection<Todo> getAllTodos() {
        return Lists.newArrayList(TodoMVC.getStorage(getSession()).findAll());
    }

    /**
     * @return not yet completed todos
     */
    public Collection<Todo> getTodos() {
        return Collections2.filter(getAllTodos(), new Predicate<Todo>() {

            @Override
            public boolean apply(Todo todo) {
                return todo.getStatus() == Status.ACTIVE || todo.getStatus() == Status.EDITING;
            }
        });
    }

    /**
     * @return all completed todos
     */
    public Collection<Todo> getCompleted() {
        return Collections2.filter(getAllTodos(), new Predicate<Todo>() {

            @Override
            public boolean apply(Todo todo) {
                return todo.getStatus() == Status.COMPLETED;
            }
        });
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forUrl("css/style.css"));
    }
}
