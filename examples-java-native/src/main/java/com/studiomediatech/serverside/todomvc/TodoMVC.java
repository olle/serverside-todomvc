package com.studiomediatech.serverside.todomvc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A single-threaded synchronous request/response TodoMVC socket-server.
 */
public class TodoMVC {
	public static void main(String[] args) throws IOException {
		try (ServerSocket server = new ServerSocket(8989)) {
			while (true) {
				try (Socket socket = server.accept()) {
					try (//
							InputStream in = socket.getInputStream(); //
							OutputStream out = socket.getOutputStream()) {
						Request request = parseRequest(in);
						Response response = handleRequest(request);
						sendResponse(response, out);
					}
				}
			}
		}
	}

	private static Request parseRequest(InputStream in) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Response handleRequest(Request req) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void sendResponse(Response resp, OutputStream out) throws IOException {
		out.write(("" //
				+ "HTTP/1.1 200 OK\n" //
				+ "Content-Type: text/plain\n" //
				+ "Content-Length: 13\n" //
				+ "Connection: close\n\n" //
				+ "hello world\n\n").getBytes());
		out.flush();
	}

	static final class Request {

	}

	static final class Response {

	}
}
