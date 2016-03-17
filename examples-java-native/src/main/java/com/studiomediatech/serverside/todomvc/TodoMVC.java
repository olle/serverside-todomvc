package com.studiomediatech.serverside.todomvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterators;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

/**
 * A single-threaded synchronous request/response TodoMVC socket-server.
 */
public class TodoMVC {

	private static ServerSocket server;

	public static void main(String[] args) throws IOException {

		server = new ServerSocket(8989);

		Socket socket;

		while (true) {
			socket = server.accept();
			{
				InputStream in = socket.getInputStream();
				InputStreamReader stream = new InputStreamReader(in);
				BufferedReader buffer = new BufferedReader(stream);
				OutputStream out = socket.getOutputStream();
				{
					Request request = parseRequest(buffer);
					Response response = handle(request);
					writeResponse(response, out);
				}
				out.flush();
				buffer.close();
				stream.close();
				out.close();
				in.close();
			}
			socket.close();
		}

	}

	private static Request parseRequest(BufferedReader buffer) throws IOException {
		String line = buffer.readLine();
		System.out.println(line);
		return null;
	}

	private static Response handle(Request request) {

		return new Response("<h1>Hello World!</h1>\n\n");
	}

	private static void writeResponse(Response response, OutputStream out) throws IOException {

		String content = response.body;

		out.write(("" //
				+ "HTTP/1.1 200 OK\n" //
				+ "Content-Type: text/html\n" //
				+ "Content-Length: " + content.length() + "\n" //
				+ "Connection: close\n\n" //
				+ content).getBytes());
	}

	static final class Request {

	}

	static final class Response {

		final String body;

		public Response(String body) {
			this.body = body;
		}
	}

}
