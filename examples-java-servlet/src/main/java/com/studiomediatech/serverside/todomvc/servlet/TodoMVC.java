package com.studiomediatech.serverside.todomvc.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studiomediatech.serverside.todomvc.domain.Task;
import com.studiomediatech.serverside.todomvc.domain.Tasks;

/**
 * Handles and directs all user intents in the todos application, providing task
 * and task list management. This is typically the "C" in MVC - the controller.
 *
 * <p>
 * TODO: There should be a View too, the "V" in MVC.
 * </p>
 */
public final class TodoMVC extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Tasks tasks = Tasks.newList();
	private final Function<HttpServlet, String> contextSupplier;

	public TodoMVC() {
		contextSupplier = s -> s.getServletContext().getContextPath();
	}

	/**
	 * Show the todos default task list view (index).
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Collection<Task> list = tasks.list();
		System.err.println(list);
		req.setAttribute("todos", list);
		req.setAttribute("completed", tasks.list(Tasks.completed()));
		forwardToView("/todos/index.jsp", req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String uri = parseURI(req);
		String view = doPostFor(uri, req, resp);
		forwardToView(view, req, resp);
	}

	String parseURI(HttpServletRequest req) {
		return req.getRequestURI()
				.replace(this.contextSupplier.apply(this), "");
	}

	String doPostFor(String uri, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {

		System.out.println("POST:" + uri);

		if (uri.equals("/todos/")) {
			String newTodo = req.getParameter("item-text");
			System.out.println("New Todo: " + newTodo);
			tasks.add(newTodo);
			redirectToIndex(resp);

			return "";
		} else if (uri.equals("toggle")) {
			int number = Integer.parseInt(req.getParameter("todo-id"));

			// TODO: `Toggle` explicit or implicit?
			tasks.complete(number);

			redirectToIndex(resp);

			return "";
		} else if (uri.equals("delete")) {
			int number = Integer.parseInt(req.getParameter("todo-id"));
			tasks.clear(number);
			redirectToIndex(resp);

			return "";
		} else if (uri.equals("clear")) {
			tasks.clear(Tasks.completed());
			redirectToIndex(resp);

			return "";
		} else {
			return "index";
		}
	}

	private void redirectToIndex(HttpServletResponse resp) throws IOException {
		resp.sendRedirect(contextSupplier.apply(this));
	}

	void forwardToView(String view, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		if (!resp.isCommitted()) {
			RequestDispatcher dispatcher = req.getRequestDispatcher(view);
			dispatcher.forward(req, resp);
		}
	}
}
