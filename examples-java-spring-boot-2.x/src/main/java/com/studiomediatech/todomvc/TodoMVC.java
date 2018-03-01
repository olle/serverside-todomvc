package com.studiomediatech.todomvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
class TodoMVC {

    public static void main(String[] args) {

        SpringApplication.run(TodoMVC.class, args);
    }


    @GetMapping("/todos")
    String todos() {

        return "Hello World!";
    }
}
