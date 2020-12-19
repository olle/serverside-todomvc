package com.studiomediatech.todomvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoMvcController {

	@GetMapping
	public String index() {
		
		return "todos.html";
	}
	
}
