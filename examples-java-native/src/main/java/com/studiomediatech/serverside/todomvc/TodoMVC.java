package com.studiomediatech.serverside.todomvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A single-threaded synchronous, blocking, request/response TodoMVC
 * socket-server.
 */
public class TodoMVC {

	private static final String HEADER_HTML;
	private static final String FOOTER_HTML;
	private static final String ACTIVE_HTML;
	private static final String COMPLETED_HTML;
	private static final String EDITING_HTML;

	private static Map<UUID, Todo> todos = new ConcurrentHashMap<>();

	static {
		HEADER_HTML = readAllLines("header.html");
		FOOTER_HTML = readAllLines("footer.html");
		ACTIVE_HTML = readAllLines("active.html");
		COMPLETED_HTML = readAllLines("completed.html");
		EDITING_HTML = readAllLines("editing.html");

	}

	private static String readAllLines(String filename) {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(ClassLoader.getSystemResourceAsStream(filename)));

		return reader.lines().map(s -> s.replaceAll(" +", " ")).collect(Collectors.joining());

	}

	public static void main(String[] args) throws IOException, URISyntaxException {

		try (ServerSocket server = new ServerSocket(8989)) {
			while (true) {
				try (Socket socket = server.accept()) {
					try ( //
							InputStream in = socket.getInputStream();
							OutputStream out = socket.getOutputStream()) {
						// Read and parse request
						Request request = parseRequest(in);
						socket.shutdownInput();

						// Produce response and write out
						Response response = handleRequest(request);
						sendResponse(response, out);
					}
				}
			}
		}
	}

	private static Request parseRequest(InputStream in) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(in), 256);
		String line = reader.readLine();

		if (Objects.isNull(line)) {
			return new Request();
		}

		String[] httpRequestItems = line.split(" ");
		String httpMethod = httpRequestItems[0];
		String requestPath = httpRequestItems[1];

		Request request = new Request(httpMethod, requestPath);

		if (request.isPostMethod()) {

			int length = 0;

			String nextLine;

			while ((nextLine = reader.readLine()) != null) {

				if (nextLine.startsWith("Content-Length:")) {
					length = Integer.parseInt(nextLine.split(":")[1].trim());
				}

				if ("".equals(nextLine.trim())) {

					if (length <= 0) {
						break;
					}

					char[] body = new char[length];
					reader.read(body, 0, length);
					request.body = new String(body);
					break;
				}

				nextLine = reader.readLine();
			}
		}

		System.out.println("PARSED REQUEST: " + request);
		return request;
	}

	private static Response handleRequest(Request req) {

		if (req.isPostMethod()) {

			if (req.hasPath("/todos") && req.hasParam("todo")) {
				addNewTodo(req.getParam("todo"));
				return Response.REDIRECT_ROOT;
			}

			if (req.hasPath("/todo") && req.hasParam("complete")) {
				markTodoAsCompleted(req.getParam("complete"));
				return Response.REDIRECT_ROOT;
			}

			if (req.hasPath("/todo") && req.hasParam("delete")) {
				deleteTodo(req.getParam("delete"));
				return Response.REDIRECT_ROOT;
			}
		}

		if (!req.isGetMethod()) {
			return Response.NOT_IMPLEMENTED;
		}

		if (req.isFavicon()) {
			return Response.NOT_FOUND;
		}

		String activeTodosResponse = getActiveTodosResponse();
		String completedTodosResponse = getCompletedTodosResponse();

		return new Response("HTTP/1.1 200 OK\n",
				HEADER_HTML + EDITING_HTML + activeTodosResponse + completedTodosResponse + FOOTER_HTML + "\n\n");
	}

	
	private static String getActiveTodosResponse() {
		return todos.values().stream().filter(Predicate.not(Todo::isCompleted))
				.map(todo -> ACTIVE_HTML.formatted(todo.getUuid(), todo.getUuid(), todo.getTodo(), todo.getUuid()))
				.collect(Collectors.joining());
	}

	private static String getCompletedTodosResponse() {

		return todos.values().stream().filter(Todo::isCompleted)
				.map(todo -> COMPLETED_HTML.formatted(todo.getUuid(), todo.getTodo(), todo.getUuid()))
				.collect(Collectors.joining());
	}

	private static void addNewTodo(String text) {
		Todo todo = new Todo(text);
		todos.put(todo.getUuid(), todo);
	}

	private static void markTodoAsCompleted(String uuid) {

		Optional.ofNullable(todos.get(UUID.fromString(uuid))).ifPresent(Todo::markCompleted);
	}

	private static void deleteTodo(String uuid) {

		todos.remove(UUID.fromString(uuid));
	}

	private static void sendResponse(Response resp, OutputStream out) throws IOException {

		byte[] responseBody = resp.body.getBytes(StandardCharsets.UTF_8);

		out.write(("" //
				+ resp.response + "Content-Type: text/html; charset=utf-8\n" //
				+ "Content-Length: " + responseBody.length + "\n" //
				+ "Connection: close\n\n" //
				+ resp.body) //
				.getBytes());

		out.flush();
	}

	static final class Request {

		private final String method;
		private final String path;
		private final String query;

		protected String body;

		public Request() {

			this("GET", "/");
		}

		public String getParam(String param) {

			if (hasParam(param)) {
				return URLDecoder.decode(body.split("=")[1], StandardCharsets.UTF_8);
			}

			return null;
		}

		public boolean hasParam(String param) {

			if (body == null) {
				return false;
			}

			return body.split("=")[0].equals(param);
		}

		public boolean hasPath(String path) {

			return this.path.equals(path);
		}

		public Request(String method, String path) {

			this.method = method;
			this.path = path;

			if (path.indexOf('?') > -1) {
				String q = "";

				try {
					q = URLDecoder.decode(path.split("\\?")[1], "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// OK.
				}

				this.query = q;
			} else {
				this.query = "";
			}
		}

		public boolean isGetMethod() {

			return method.equals("GET");
		}

		public boolean isPostMethod() {

			return method.equals("POST");
		}

		public boolean isFavicon() {

			return path.contains("/favicon.ico");
		}

		@Override
		public String toString() {

			return "Request [method=" + method + ", path=" + path + ", query=" + query + ", body=" + body + "]";
		}
	}

	static final class Response {

		public static final Response REDIRECT_ROOT = new Response("HTTP/1.1 301 Moved Permanently\nLocation: /\n\n");
		public static final Response NOT_FOUND = new Response("HTTP/1.1 404 Not Found\n");
		public static final Response NOT_IMPLEMENTED = new Response("HTTP/1.1 501 Not Implemented\n");

		private final String response;
		private final String body;

		public Response(String response) {

			this(response, "");
		}

		public Response(String response, String body) {

			this.response = response;
			this.body = body;
		}
	}

	static final class Todo {

		private final UUID uuid;
		private final String todo;

		private boolean completed;

		public Todo(String todo) {
			this.uuid = UUID.randomUUID();
			this.todo = todo;
		}

		public UUID getUuid() {
			return uuid;
		}

		public String getTodo() {
			return todo;
		}

		public boolean isCompleted() {
			return completed;
		}

		public void markCompleted() {
			this.completed = true;
		}
	}

}
