package com.studiomediatech.todomvc.app.todos;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
class AllTodosInterceptor extends HandlerInterceptorAdapter {

	private final TodoService todoService;

	@Autowired
	public AllTodosInterceptor(TodoService todoService) {
		this.todoService = todoService;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		Optional.ofNullable(modelAndView)
				.ifPresent(mav -> mav.getModelMap().addAttribute("todos", todoService.getTodos()));
	}
}
