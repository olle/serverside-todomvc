package com.studiomediatech.serverside.todomvc.servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start {

  public static void main(String[] args) throws Exception {

    Server server = new Server(8080);

    ServerConnector connector = new ServerConnector(server);
    connector.setAcceptQueueSize(1);

    WebAppContext context = new WebAppContext();
    context.setContextPath("/serverside-todomvc-servlet");
    context.setResourceBase("src/main/webapp");
    server.setHandler(context);

    try {
      System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
      server.start();
      System.in.read();
      System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
      server.stop();
      server.join();
    }
    catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    finally {
      connector.stop();
      connector.close();
    }
  }
}
