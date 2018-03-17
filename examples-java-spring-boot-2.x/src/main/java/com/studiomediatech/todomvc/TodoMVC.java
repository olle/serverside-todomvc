package com.studiomediatech.todomvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@EnableAutoConfiguration
class TodoMVC {

    @GetMapping("/todos")
    @ResponseBody
    String todos() {

        return "Hello World!";
    }


    public static void main(String[] args) {

        SpringApplication.run(TodoMVC.class, args);
    }
}
