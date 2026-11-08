package com.studiomediatech.todomvc;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.Fields;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class TodoMVC {

    private static final String TEMPLATE_NAME = "templates/index.mustache";

    private static final Mustache template = new DefaultMustacheFactory().compile(TEMPLATE_NAME);

    private static final TodoService service = new TodoService();

    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);
        server.setHandler(new TodoHandler());
        server.start();
        server.join();
    }


    static final class TodoHandler extends Handler.Abstract {

        @Override
        public boolean handle(Request request, Response response, Callback callback) throws Exception {

            String path = Request.getPathInContext(request);
            String method = request.getMethod();

            try {
                if ("GET".equals(method)) {

                    if ("/".equals(path)) {
                        render(response, callback);
                        return true;
                    }

                    if ("/style.css".equals(path)) {
                        return serveStyle(request, response, callback);
                    }
                }

                if ("POST".equals(method)) {

                    Fields params = Request.getParameters(request);

                    if ("/todos".equals(path)) {
                        service.addNewTodo(params.getValue("todo"));
                        return redirect(request, response, callback);
                    }

                    if (path.startsWith("/todos/")) {
                        String id = params.getValue("id");
                        String update = params.getValue("update");

                        if (id != null && update != null) {
                            service.updateTodo(id, update);
                        }

                        return redirect(request, response, callback);
                    }

                    if ("/todo".equals(path)) {
                        withParam(params, "complete", service::completeTodoItem);
                        withParam(params, "revert", service::activateTodoItem);
                        withParam(params, "delete", service::deleteTodoItem);
                        withParam(params, "edit", service::editTodo);

                        return redirect(request, response, callback);
                    }

                    if ("/controls".equals(path)) {
                        withParam(params, "hide", service::hideCompleted);
                        withParam(params, "show", service::showCompleted);
                        withParam(params, "clear", service::clearCompleted);

                        return redirect(request, response, callback);
                    }
                }

                Response.writeError(request, response, callback, HttpStatus.NOT_FOUND_404);
                return true;
            } catch (Exception e) {
                Response.writeError(request, response, callback, e);
                return true;
            }
        }


        private static void render(Response response, Callback callback) throws IOException {

            String html;

            try (StringWriter writer = new StringWriter()) {
                template.execute(writer, viewModel()).flush();
                html = writer.toString();
            }

            response.getHeaders().put(HttpHeader.CONTENT_TYPE, "text/html; charset=UTF-8");
            response.write(true, ByteBuffer.wrap(html.getBytes(StandardCharsets.UTF_8)), callback);
        }


        private static boolean serveStyle(Request request, Response response, Callback callback) throws IOException {

            try (InputStream style = TodoMVC.class.getResourceAsStream("/public/style.css")) {

                if (style == null) {
                    Response.writeError(request, response, callback, HttpStatus.NOT_FOUND_404);
                    return true;
                }

                response.getHeaders().put(HttpHeader.CONTENT_TYPE, "text/css; charset=UTF-8");
                response.write(true, ByteBuffer.wrap(style.readAllBytes()), callback);
                return true;
            }
        }


        private static boolean redirect(Request request, Response response, Callback callback) {

            Response.sendRedirect(request, response, callback, "/");
            return true;
        }


        private static void withParam(Fields params, String name, Consumer<String> handler) {

            String value = params.getValue(name);

            if (value != null) {
                handler.accept(value);
            }
        }
    }


    private static Map<String, Object> viewModel() {

        Map<String, Object> viewModel = new HashMap<>();

        List<Todo> active = service.getActiveTodos();
        viewModel.put("active", active);
        viewModel.put("activeCount", active.size());

        List<Todo> completed = service.getCompletedTodos();
        viewModel.put("completed", completed);
        viewModel.put("completedCount", completed.size());

        viewModel.put("hidden", service.isHidden());
        viewModel.put("editing", service.isEditing());

        return viewModel;
    }
}
