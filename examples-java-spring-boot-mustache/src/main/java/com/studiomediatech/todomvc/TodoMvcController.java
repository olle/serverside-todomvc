package com.studiomediatech.todomvc;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TodoMvcController {

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

        return "redirect:/";
    }
}
