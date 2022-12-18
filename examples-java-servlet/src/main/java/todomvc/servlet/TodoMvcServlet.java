package todomvc.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import todomvc.domain.TodoItem;
import todomvc.repository.Repository;
import todomvc.repository.SimpleHashMapStorage;
import todomvc.repository.Storage;

public final class TodoMvcServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final Storage<TodoItem, Long> storage = new SimpleHashMapStorage();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.err.println("GET " + req);

		Repository<TodoItem, Long> repo = storage.forKey(req.getRequestedSessionId());

		req.setAttribute("active", repo.fetchActive());
		req.setAttribute("completed", repo.fetchCompleted());
		req.setAttribute("editing", repo.isEditing());

		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		var uri = req.getRequestURI();
		var params = req.getParameterMap();

		System.err.println("POST " + uri + "  " + params);

		if (uri.equals("/todos.do")) {

			if (params.containsKey("todo")) {
				String todoText = params.get("todo")[0];
				addNewTodoItem(todoText, req.getRequestedSessionId());
			} else if (params.containsKey("update") && params.containsKey("id")) {
				var todoId = params.get("id")[0];
				var todoText = params.get("update")[0];
				updateTodoItem(todoId, todoText, req.getRequestedSessionId());
			}

		} else if (uri.equals("/todo.do")) {

			if (params.containsKey("complete")) {
				var todoId = params.get("complete")[0];
				markTodoItemAsCompleted(todoId, req.getRequestedSessionId());
			} else if (params.containsKey("revert")) {
				var todoId = params.get("revert")[0];
				markTodoItemAsActive(todoId, req.getRequestedSessionId());
			} else if (params.containsKey("delete")) {
				var todoId = params.get("delete")[0];
				deleteTodo(todoId, req.getRequestedSessionId());
			} else if (params.containsKey("edit")) {
				var todoId = params.get("edit")[0];
				markTodoAsEditing(todoId, req.getRequestedSessionId());
			}

		} else if (uri.equals("/controls.do")) {

			if (params.containsKey("clear")) {
				clearAllCompleted(req.getRequestedSessionId());
			}
		}

		resp.sendRedirect("/index.do");
	}

	private void updateTodoItem(String todoId, String todoText, String requestedSessionId) {
		storage.forKey(requestedSessionId).updateTodoItem(Long.parseLong(todoId), todoText);		
	}

	private void markTodoAsEditing(String todoId, String requestedSessionId) {
		storage.forKey(requestedSessionId).markEditingById(Long.parseLong(todoId));
	}

	private void clearAllCompleted(String requestedSessionId) {
		storage.forKey(requestedSessionId).clearAllCompletedTodoItems();
	}

	private void addNewTodoItem(String todoText, String requestedSessionId) {
		storage.forKey(requestedSessionId).createNewTodoItem(todoText);
	}

	private void markTodoItemAsCompleted(String todoId, String requestedSessionId) {
		storage.forKey(requestedSessionId).markCompletedById(Long.parseLong(todoId));
	}

	private void markTodoItemAsActive(String todoId, String requestedSessionId) {
		storage.forKey(requestedSessionId).markActiveById(Long.parseLong(todoId));
	}

	private void deleteTodo(String todoId, String requestedSessionId) {
		storage.forKey(requestedSessionId).deleteById(Long.parseLong(todoId));
	}
}
