package com.studiomediatech.todomvc.app.todos;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.studiomediatech.todomvc.web.TodoDto;

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

		if (modelAndView == null) {
			return;
		}

		final AtomicBoolean editingAny = new AtomicBoolean(false);
		final AtomicInteger completed = new AtomicInteger(0);

		List<TodoDto> todos = todoService.getTodos().stream().map(TodoDto::new).map(t -> {
			if (t.isEditing()) {
				editingAny.set(true);
			} else if (t.isCompleted()) {
				completed.incrementAndGet();
			}
			return t;
		}).collect(Collectors.toList());

		ModelMap modelMap = modelAndView.getModelMap();
		
		modelMap.addAttribute("todos", todos);
		modelMap.addAttribute("completed", completed.get());
		modelMap.addAttribute("editing", editingAny.get());		
	}
}
