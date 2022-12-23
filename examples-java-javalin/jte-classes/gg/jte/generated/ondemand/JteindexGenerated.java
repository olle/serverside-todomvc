package gg.jte.generated.ondemand;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {10,10,10,10,10,10,16,22,28,37,43,51,58,62,70,76,94,106};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("<!doctype html>\n<html lang=\"en\">\n\n<head>\n  <meta charset=\"utf-8\">\n  <meta name=\"description\"\n    content=\"Helping you remember or select a server-side MV* framework - Todo apps for Spring Boot, Flask, PHP and many more\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n  <title>Java Javalin JTE • Server-Side TodoMVC</title>\n  <link rel=\"stylesheet\" href=\"/style.css\">\n  ");
		jteOutput.writeContent("\n</head>\n\n<body>\n  <main>\n\n    ");
		jteOutput.writeContent("\n    <h1>Todos <small title=\"{active-count} Active items\">{active-count}</small></h1>\n    <form action=\"controls\" method=\"post\">\n      ");
		jteOutput.writeContent("\n      <button name=\"clear\" value=\"completed\" title=\"Clear {completed-count} completed\">{completed-count} Completed • Clear</button>\n\n      ");
		jteOutput.writeContent("\n      <button name=\"hide\" value=\"completed\" title=\"Hide completed todo items\">Hide</button>\n      <button name=\"show\" value=\"completed\" title=\"Show completed todo items\">Show</button>\n    </form>\n\n\n    ");
		jteOutput.writeContent("\n    <form action=\"todos\" method=\"post\">\n      <label for=\"todo\">Todo</label>\n      ");
		jteOutput.writeContent("\n      <input placeholder=\"What needs to be done?\" autofocus required autocomplete=\"off\" name=\"todo\" id=\"todo\" />\n    </form>\n\n\n    ");
		jteOutput.writeContent("\n    <form id=\"todo-item\" method=\"post\" action=\"todo\"></form>\n    <ul>\n      ");
		jteOutput.writeContent("\n\n      <li> ");
		jteOutput.writeContent("\n        <button name=\"complete\" value=\"{todo-id}\" form=\"todo-item\" title=\"Mark completed\"></button>\n        <form class=\"inline\" method=\"post\" action=\"todos/{todo-id}\">\n          <input type=\"hidden\" name=\"id\" value=\"{todo-id}\" />\n          <input name=\"update\" value=\"{todo-text}\" autofocus required autocomplete=\"off\" />\n        </form>\n      </li>\n\n      <li> ");
		jteOutput.writeContent("\n        <button name=\"complete\" value=\"{todo-id}\" form=\"todo-item\" title=\"Mark completed\"></button>\n        <button name=\"edit\" value=\"{todo-id}\" form=\"todo-item\" title=\"Click to edit\">{todo-text}</button>\n        <button name=\"delete\" value=\"{todo-id}\" form=\"todo-item\" title=\"Delete todo item\">&#x2715;</button>\n      </li>\n\n      ");
		jteOutput.writeContent("\n      <li>\n        <button name=\"revert\" value=\"{todo-id}\" form=\"todo-item\" title=\"Mark as active\"></button>\n        <span>{todo-text}</span>\n        <button name=\"delete\" value=\"{todo-id}\" form=\"todo-item\" title=\"Delete todo item\">&#x2715;</button>\n      </li>\n    </ul>\n  </main>\n  <footer>\n    <em>Click on the text to edit a todo.</em>\n    <p>\n      Template by <a href=\"https://github.com/olle\">Olle Törnström</a>,\n      inspired by the original <a href=\"https://todomvc.com\">TodoMVC</a> from\n      <a href=\"http://github.com/sindresorhus\">Sindre Sorhus</a>.\n    </p>\n    ");
		jteOutput.writeContent("\n    <p>\n      Created by <a href=\"http://todomvc.com\">you</a>.\n    </p>\n    <p>\n      Part of <a href=\"http://github.com/olle/serverside-todomvc\">Server-Side TodoMVC</a>\n    </p>\n  </footer>\n</body>\n\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
