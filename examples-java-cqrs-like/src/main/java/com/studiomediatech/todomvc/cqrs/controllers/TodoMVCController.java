package com.studiomediatech.todomvc.cqrs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TodoMVCController {

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public String sayHelloToTheWorld() {
		return "Hello World!";
	}
}
