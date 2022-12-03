package com.studiomediatech.todomvc;

import java.util.Optional;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class TodoMvcController {

	private static final String VIEW = "index";
	private static final String REDIRECT = "redirect:/";

	private final TodoMvcService service;

	public TodoMvcController(TodoMvcService service) {

		this.service = service;
	}

	@GetMapping("/")
	public String index(Model model, HttpSession session) {

		boolean hideCompleted = justHideCompletedFlag(session);
		model.mergeAttributes(service.fetchAttributesForIndexPage(hideCompleted));

		return VIEW;
	}

	private boolean justHideCompletedFlag(HttpSession session) {

		return Optional.ofNullable(session.getAttribute("hideCompleted")).map(Boolean.class::cast).orElse(Boolean.FALSE)
				.booleanValue();
	}

	@PostMapping(path = "controls", params = { "hide" })
	public String toggleHide(HttpSession session, @RequestParam("hide") String hide) {

		boolean hideCompleted = "completed".equals(hide);
		session.setAttribute("hideCompleted", Boolean.valueOf(hideCompleted));

		return REDIRECT;
	}

	@PostMapping(path = "controls", params = { "clear" })
	public String clearCompleted() {

		service.clearCompletedTodos();

		return REDIRECT;
	}

	@PostMapping(path = "todos")
	public String createNewTodo(@RequestParam("todo") String todo) {

		if (!StringUtils.hasText(todo)) {
			return REDIRECT;
		}

		service.createNewTodo(todo);

		return REDIRECT;
	}

	@PostMapping(path = "todos/{uuid}", params = { "update", "id" })
	public String updateTodo(@RequestParam("update") String update, @RequestParam("id") String uuid) {

		service.updateTodo(uuid, update);

		return REDIRECT;
	}

	@PostMapping(path = "todo", params = { "edit" })
	public String editTodo(Model model, HttpSession session, @RequestParam("edit") String uuid) {

		boolean hideCompleted = justHideCompletedFlag(session);
		model.mergeAttributes(service.fetchAttributesForEditPage(uuid, hideCompleted));

		return VIEW;
	}

	@PostMapping(path = "todo", params = { "complete" })
	public String markTodoAsCompleted(@RequestParam("complete") String uuid) {

		service.markTodoAsCompleted(uuid);

		return REDIRECT;
	}

	@PostMapping(path = "todo", params = { "revert" })
	public String markTodoAsActive(@RequestParam("revert") String uuid) {

		service.markTodoAsActive(uuid);

		return REDIRECT;
	}

	@PostMapping(path = "todo", params = { "delete" })
	public String deleteTodo(@RequestParam("delete") String uuid) {

		service.deleteTodo(uuid);

		return REDIRECT;
	}

}
