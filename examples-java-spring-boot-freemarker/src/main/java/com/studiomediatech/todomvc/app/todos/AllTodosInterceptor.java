package com.studiomediatech.todomvc.app.todos;

import com.studiomediatech.todomvc.web.TodoDto;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import org.springframework.ui.ModelMap;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
        final AtomicInteger active = new AtomicInteger(0);

        todoService.getTodos().stream().map(TodoDto::new).forEach(t -> {
            if (t.isEditing()) {
                editingAny.set(true);
            } else if (t.isCompleted()) {
                completed.incrementAndGet();
            } else if (t.isActive()) {
                active.incrementAndGet();
            }
        });

        ModelMap modelMap = modelAndView.getModelMap();

        modelMap.addAttribute("completed", completed.get());
        modelMap.addAttribute("editing", editingAny.get());
        modelMap.addAttribute("active", active.get());
    }
}
