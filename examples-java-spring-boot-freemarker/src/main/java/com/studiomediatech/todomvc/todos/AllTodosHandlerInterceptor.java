package com.studiomediatech.todomvc.todos;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
class AllTodosHandlerInterceptor extends HandlerInterceptorAdapter {

	private final TodoService todoService;

	@Autowired
	public AllTodosHandlerInterceptor(TodoService todoService) {
		this.todoService = todoService;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		Optional.ofNullable(modelAndView).ifPresent(m -> m.getModelMap().addAttribute("todos", todoService.getTodos()));
	}
}
