package com.studiomediatech.todomvc;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TodoMvcController {

    private static final String REDIRECT = "redirect:/";

    private final TodoMvcService service;

    public TodoMvcController(TodoMvcService service) {

        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {

        model.mergeAttributes(service.fetchAttributesForIndexPage());

        return "index";
    }


    @PostMapping("/")
    public String createNewTodo(String todo) {

        service.createNewTodo(todo);

        return REDIRECT;
    }


    @PostMapping(path = "/", params = { "complete" })
    public String markTodoAsCompleted(@RequestParam("complete") String uuid) {

        service.markTodoAsCompleted(uuid);

        return REDIRECT;
    }


    @PostMapping(path = "/", params = { "delete" })
    public String deleteTodo(@RequestParam("delete") String uuid) {

        service.deleteTodo(uuid);

        return REDIRECT;
    }
    
    @PostMapping(path = "/", params = { "revert" })
    public String markTodoAsNotCompleted(@RequestParam("revert") String uuid) {
    	
    	service.markTodoAsNotCompleted(uuid);
    	
    	return REDIRECT;
    }
}
