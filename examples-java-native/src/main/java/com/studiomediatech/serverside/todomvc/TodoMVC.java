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
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A single-threaded synchronous, blocking, request/response TodoMVC
 * socket-server.
 */
public class TodoMVC {

	private static String header;
	private static String footer;

	public static void main(String[] args) throws IOException, URISyntaxException {

		header = readAllLines("header.html");
		footer = readAllLines("footer.html");

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

	private static String readAllLines(String filename) {

		InputStream in = ClassLoader.getSystemResourceAsStream(filename);
		InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);

		return new BufferedReader(reader).lines().collect(Collectors.joining());
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
		System.out.println("PARSED REQUEST: " + request);

		return request;
	}

	private static Response handleRequest(Request req) {

		if (!req.isGetMethod()) {
			return Response.NOT_IMPLEMENTED;
		}

		if (req.isFavicon()) {
			return Response.NOT_FOUND;
		}

		return new Response("HTTP/1.1 200 OK\n", header + "\n<!-- here we go -->\n" + footer + "\n\n");
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

		public Request() {

			this("GET", "/");
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

		public boolean isFavicon() {

			return path.contains("/favicon.ico");
		}

		@Override
		public String toString() {

			return "Request [method=" + method + ", path=" + path + ", query=" + query + "]";
		}
	}

	static final class Response {

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
}
