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
				String line = buffer.readLine();
				System.out.println(Arrays.asList(line.split(" ")));
				OutputStream out = socket.getOutputStream();
				out.write(("" //
						+ "HTTP/1.1 200 OK\n" //
						+ "Content-Type: text/plain\n" //
						+ "Content-Length: 13\n" //
						+ "Connection: close\n\n" //
						+ "hello world\n\n").getBytes());
				out.flush();
				buffer.close();
				stream.close();
				out.close();
				in.close();
			}
			socket.close();
		}

	}

}
