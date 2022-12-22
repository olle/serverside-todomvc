package todomvc;

import io.javalin.Javalin;

public class TodoMVC {

	public static void main(String[] args) {
		var app = Javalin
				.create(/* config */)
				.get("/", ctx -> ctx.result("Hello World"))
				.start(8080);
	}
}
