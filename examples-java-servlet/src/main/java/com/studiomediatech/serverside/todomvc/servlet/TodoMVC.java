package com.studiomediatech.serverside.todomvc.servlet;

import com.studiomediatech.serverside.todomvc.domain.Tasks;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Handles and directs all user intents in the todos application, providing task and task list management. This is
 * typically the "C" in MVC - the controller.
 *
 * <p>TODO: There should be a View too, the "V" in MVC.</p>
 */
public final class TodoMVC extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final Tasks tasks;

    public TodoMVC() {

        tasks = Tasks.newList();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        handleRequest(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        handleRequest(req, resp);
    }


    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String command = parseCommand(req);
        String view = dispatchControl(command, req, resp);
        forwardToView(view, req, resp);
    }


    String parseCommand(HttpServletRequest req) {

        String requestURI = req.getRequestURI();
        int lastSlashIdx = requestURI.lastIndexOf("/");
        int lastDotIdx = requestURI.lastIndexOf(".");

        return requestURI.substring(lastSlashIdx + 1, lastDotIdx);
    }


    String dispatchControl(String command, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // final Repository<Todo, Long> repository = getSessionRepository(req);

        if (command.equals("home")) {
            req.setAttribute("todos", tasks.list());
            req.setAttribute("completed", tasks.list(Tasks.completed()));

            return "home";
        } else if (command.equals("new")) {
            String newTodo = req.getParameter("new-todo");
            tasks.add(newTodo);
            resp.sendRedirect("home.do");

            return "";
        } else if (command.equals("toggle")) {
            int number = Integer.parseInt(req.getParameter("todo-id"));

            // TODO: `Toggle` explicit or implicit?
            tasks.complete(number);

            resp.sendRedirect("home.do");

            return "";
        } else if (command.equals("delete")) {
            int number = Integer.parseInt(req.getParameter("todo-id"));
            tasks.clear(number);
            resp.sendRedirect("home.do");

            return "";
        } else if (command.equals("clear")) {
            tasks.clear(Tasks.completed());
            resp.sendRedirect("home.do");

            return "";
        } else {
            return "home";
        }
    }


    void forwardToView(String view, HttpServletRequest req, HttpServletResponse resp) throws ServletException,
        IOException {

        if (!resp.isCommitted()) {
            String path = "/" + view + ".jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(path);
            dispatcher.forward(req, resp);
        }
    }
}
