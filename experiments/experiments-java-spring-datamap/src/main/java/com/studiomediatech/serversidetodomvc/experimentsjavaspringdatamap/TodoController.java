package com.studiomediatech.serversidetodomvc.experimentsjavaspringdatamap;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TodoController implements Loggable {

	private final TodoService todoService;

	public TodoController(TodoService service) {
		this.todoService = service;
	}

	@GetMapping("/")
	public String getTodos(Model model) {
		logger().debug("Handling request to list all todos");
		Collection<Todo> todos = todoService.getAllTodos();
		logger().debug("Retrieved {} todos to list", todos.size());
		model.addAttribute("todos", todos);
		logger().debug("Done handling list request");
		return "index";
	}

	@PostMapping("/")
	public String addTodo(@RequestBody MultiValueMap<String, Object> data) {
		logger().debug("Handling request to save todo: {}", data);
		todoService.addNewTodo(data);
		return "redirect:/";
	}
}