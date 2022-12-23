package gg.jte.generated.ondemand;
import java.util.List;
import todomvc.TodoMVC.Todo;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,18,18,23,23,23,23,23,23,25,25,25,25,25,25,26,35,41,49,56,56,57,57,59,59,59,59,59,59,59,59,60,60,60,61,61,61,61,61,61,61,61,65,65,66,67,67,67,67,67,67,67,67,68,68,68,68,68,68,68,68,68,68,68,69,69,69,69,69,69,69,69,71,71,72,72,74,78,78,81,81,81,84,84,94,106};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Long activeCount, Long completedCount, List<Todo> active, List<Todo> completed) {
		jteOutput.writeContent("\n<!doctype html>\n<html lang=\"en\">\n\n<head>\n  <meta charset=\"utf-8\">\n  <meta name=\"description\"\n    content=\"Helping you remember or select a server-side MV* framework - Todo apps for Spring Boot, Flask, PHP and many more\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n  <title>Java Javalin JTE • Server-Side TodoMVC</title>\n  <link rel=\"stylesheet\" href=\"/style.css\">\n  ");
		jteOutput.writeContent("\n</head>\n\n<body>\n  <main>\n    <h1>Todos <small title=\"");
		jteOutput.setContext("small", "title");
		jteOutput.writeUserContent(activeCount);
		jteOutput.writeContent(" Active items\">");
		jteOutput.setContext("small", null);
		jteOutput.writeUserContent(activeCount);
		jteOutput.writeContent("</small></h1>\n    <form action=\"controls\" method=\"post\">\n      <button name=\"clear\" value=\"completed\" title=\"Clear ");
		jteOutput.setContext("button", "title");
		jteOutput.writeUserContent(completedCount);
		jteOutput.writeContent(" completed\">");
		jteOutput.setContext("button", null);
		jteOutput.writeUserContent(completedCount);
		jteOutput.writeContent(" Completed • Clear</button>\n      ");
		jteOutput.writeContent("\n      <button name=\"hide\" value=\"completed\" title=\"Hide completed todo items\">Hide</button>\n      <button name=\"show\" value=\"completed\" title=\"Show completed todo items\">Show</button>\n    </form>\n\n\n    ");
		jteOutput.writeContent("\n    <form action=\"todos\" method=\"post\">\n      <label for=\"todo\">Todo</label>\n      ");
		jteOutput.writeContent("\n      <input placeholder=\"What needs to be done?\" autofocus required autocomplete=\"off\" name=\"todo\" id=\"todo\" />\n    </form>\n\n\n    ");
		jteOutput.writeContent("\n    <form id=\"todo-item\" method=\"post\" action=\"todo\"></form>\n    <ul>\n      ");
		for (Todo todo : active) {
			jteOutput.writeContent("\n        ");
			if (todo.isEditing()) {
				jteOutput.writeContent("\n          <li>\n            <button name=\"complete\"");
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(todo.getId())) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("button", "value");
					jteOutput.writeUserContent(todo.getId());
					jteOutput.setContext("button", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" form=\"todo-item\" title=\"Mark completed\"></button>\n            <form class=\"inline\" method=\"post\" action=\"todos/");
				jteOutput.setContext("form", "action");
				jteOutput.writeUserContent(todo.getId());
				jteOutput.writeContent("\">\n              <input type=\"hidden\" name=\"id\"");
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(todo.getId())) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("input", "value");
					jteOutput.writeUserContent(todo.getId());
					jteOutput.setContext("input", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" />\n              <input name=\"update\" value=\"{todo-text}\" autofocus required autocomplete=\"off\" />\n            </form>\n          </li>\n        ");
			} else {
				jteOutput.writeContent("\n          <li> ");
				jteOutput.writeContent("\n            <button name=\"complete\"");
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(todo.getId())) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("button", "value");
					jteOutput.writeUserContent(todo.getId());
					jteOutput.setContext("button", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" form=\"todo-item\" title=\"Mark completed\"></button>\n            <button name=\"edit\"");
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(todo.getId())) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("button", "value");
					jteOutput.writeUserContent(todo.getId());
					jteOutput.setContext("button", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" form=\"todo-item\" title=\"Click to edit\">");
				jteOutput.setContext("button", null);
				jteOutput.writeUserContent(todo.getText());
				jteOutput.writeContent("</button>\n            <button name=\"delete\"");
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(todo.getId())) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("button", "value");
					jteOutput.writeUserContent(todo.getId());
					jteOutput.setContext("button", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" form=\"todo-item\" title=\"Delete todo item\">&#x2715;</button>\n          </li>\n        ");
			}
			jteOutput.writeContent("\n      ");
		}
		jteOutput.writeContent("\n\n      ");
		jteOutput.writeContent("\n      ");
		for (Todo todo : completed) {
			jteOutput.writeContent("\n      <li>\n        <button name=\"revert\" value=\"{todo.getId()}\" form=\"todo-item\" title=\"Mark as active\"></button>\n        <span>");
			jteOutput.setContext("span", null);
			jteOutput.writeUserContent(todo.getText());
			jteOutput.writeContent("</span>\n        <button name=\"delete\" value=\"{todo.getId()}\" form=\"todo-item\" title=\"Delete todo item\">&#x2715;</button>\n      </li>\n      ");
		}
		jteOutput.writeContent("\n    </ul>\n  </main>\n  <footer>\n    <em>Click on the text to edit a todo.</em>\n    <p>\n      Template by <a href=\"https://github.com/olle\">Olle Törnström</a>,\n      inspired by the original <a href=\"https://todomvc.com\">TodoMVC</a> from\n      <a href=\"http://github.com/sindresorhus\">Sindre Sorhus</a>.\n    </p>\n    ");
		jteOutput.writeContent("\n    <p>\n      Created by <a href=\"http://todomvc.com\">you</a>.\n    </p>\n    <p>\n      Part of <a href=\"http://github.com/olle/serverside-todomvc\">Server-Side TodoMVC</a>\n    </p>\n  </footer>\n</body>\n\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Long activeCount = (Long)params.get("activeCount");
		Long completedCount = (Long)params.get("completedCount");
		List<Todo> active = (List<Todo>)params.get("active");
		List<Todo> completed = (List<Todo>)params.get("completed");
		render(jteOutput, jteHtmlInterceptor, activeCount, completedCount, active, completed);
	}
}
