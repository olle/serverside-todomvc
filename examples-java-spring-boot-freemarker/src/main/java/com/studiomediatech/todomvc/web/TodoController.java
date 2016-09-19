package com.studiomediatech.todomvc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.studiomediatech.todomvc.app.todos.Todo;
import com.studiomediatech.todomvc.app.todos.TodoService;

@Controller
class TodoController {

	private final TodoService todoService;

	@Autowired
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index() {		
		return "todos";
	}
	
	@RequestMapping(path = "/todos", method = RequestMethod.POST)
	public String newTodo(@RequestParam("item-text") String itemText) {
		
		Todo todo = new Todo(itemText);
		todoService.saveTodo(todo);
		
		return "redirect:/";
	}
	
	@RequestMapping(path = "/todos/{id}", method = RequestMethod.DELETE)
	public String deleteTodo(@PathVariable Long id) {
		
		todoService.deleteTodo(id);
		
		return "redirect:/";
		
	}
	
}
