package com.studiomediatech.todomvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TodoMvcController {

	@GetMapping("/")
	public String index(Model model) {

		// TODO: Get count from store.
		model.addAttribute("activeCount", 0);

		return "index";
	}

	@PostMapping("/")
	public String createNewTodo(String todo) {

		// TODO: Add todo.

		return "redirect:/";
	}
}
