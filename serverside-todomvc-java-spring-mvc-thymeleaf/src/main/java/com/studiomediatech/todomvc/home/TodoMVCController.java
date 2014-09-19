package com.studiomediatech.todomvc.home;

import java.util.List;

import javax.inject.Inject;

import com.studiomediatech.todomvc.TodoService;
import com.studiomediatech.todomvc.domain.TodoEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TodoMVCController {

  @Inject
  TodoService todoService;

  @ModelAttribute("newTodo")
  public TodoEntity createNewTodo() {
    return this.todoService.newTodo();
  }

  @ModelAttribute("allTodos")
  public List<TodoEntity> populateTodos() {
    return this.todoService.findAll();
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index() {
    return "home";
  }

  @RequestMapping(value = "/saveTodo", method = RequestMethod.POST)
  public String saveTodo(TodoEntity todo, BindingResult bindingResult, ModelMap model) {

    if (bindingResult.hasErrors()) {
      return "home";
    }

    this.todoService.save(todo);
    model.clear();

    return "home";
  }

}
