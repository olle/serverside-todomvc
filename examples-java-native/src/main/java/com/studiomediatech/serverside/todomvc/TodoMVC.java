package com.studiomediatech.serverside.todomvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.stream.Collectors;


/**
 * A single-threaded synchronous, blocking, request/response TodoMVC socket-server.
 */
public class TodoMVC {

    private static String header;
    private static String footer;

    public static void main(String[] args) throws IOException {

        header = readAllLines("header.html");
        footer = readAllLines("footer.html");

        try(ServerSocket server = new ServerSocket(8989)) {
            while (true) {
                try(Socket socket = server.accept()) {
                    try( //
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


    private static String readAllLines(String fn) {

        return new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fn))).lines()
            .collect(Collectors.joining());
    }


    private static Request parseRequest(InputStream in) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in), 512);

        String line = reader.readLine();
        String[] httpRequestItems = line.split(" ");

        String httpMethod = httpRequestItems[0];
        String requestPath = httpRequestItems[1];

        return new Request(httpMethod, requestPath);
    }


    private static Response handleRequest(Request req) {

        if (!req.isGetMethod()) {
            return Response.NOT_IMPLEMENTED;
        }

        if (req.isFavicon()) {
            return Response.NOT_FOUND;
        }

        return new Response("HTTP/1.1 200 OK\n", header + "<h1>Hello World!</h1>" + footer);
    }


    private static void sendResponse(Response resp, OutputStream out) throws IOException {

        out.write(
            ("" //
                + resp.response
                + "Content-Type: text/html\n" //
                + "Content-Length: " + resp.body.length() + "\n" //
                + "Connection: close\n\n" //
                + resp.body).getBytes());

        out.flush();
    }

    static final class Request {

        private final String method;
        private final String path;

        public Request(String method, String path) {

            this.method = method;
            this.path = path;
        }

        public boolean isGetMethod() {

            return method.equals("GET");
        }


        public boolean isFavicon() {

            return path.contains("/favicon.ico");
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
