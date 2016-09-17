package com.studiomediatech.todomvc.todos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {

	private static final String INDEX_VIEW = "index";

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@RequestMapping(name = "/", method = RequestMethod.GET)
	public String index() {
		return INDEX_VIEW;
	}
	
	@RequestMapping(name = "/todos", method = RequestMethod.POST)
	public String addTodo(@RequestParam("item-text") String itemText) {
		
		Todo todo = new Todo(itemText);		
		todoService.saveTodo(todo);		
		
		return INDEX_VIEW;
	}
	
}
