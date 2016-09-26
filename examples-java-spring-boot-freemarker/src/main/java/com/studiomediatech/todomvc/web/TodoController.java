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

	private static final String REDIRECT_TO_INDEX = "redirect:/";
	private final TodoService todoService;

	@Autowired
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index() {		
		return "todos";
	}
	
	@RequestMapping(path ="/", method = RequestMethod.GET, params = {"action", "item-id"})
	public String editTodo(@RequestParam("item-id") Long id) {
		todoService.editTodo(id);
		return REDIRECT_TO_INDEX;
	}
	
	@RequestMapping(path = "/todos", method = RequestMethod.POST)
	public String newTodo(@RequestParam("item-text") String itemText) {
		Todo todo = new Todo(itemText);
		todoService.saveTodo(todo);
		return REDIRECT_TO_INDEX;
	}
	
	@RequestMapping(path = "/todos", method = RequestMethod.DELETE, params = {"filter=completed"})
	public String clearCompleted() {	
		todoService.clearCompleted();		
		return REDIRECT_TO_INDEX;
	}
	
	@RequestMapping(path = "/todos/{id}", method = RequestMethod.DELETE)
	public String deleteTodo(@PathVariable Long id) {
		todoService.deleteTodo(id);
		return REDIRECT_TO_INDEX;
	}
	
	@RequestMapping(path = "/todos/{id}", method = RequestMethod.POST)
	public String editTodo(@PathVariable Long id, @RequestParam("item-text") String itemText) {
		todoService.updateTodo(id, itemText);
		return REDIRECT_TO_INDEX;
	}
	
	@RequestMapping(path = "/todos/{id}/status", method = RequestMethod.POST)
	public String toggleStatus(@PathVariable Long id) {
		todoService.toggleStatus(id);
		return REDIRECT_TO_INDEX;
	}
	
}
