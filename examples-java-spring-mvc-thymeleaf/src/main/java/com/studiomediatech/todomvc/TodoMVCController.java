package com.studiomediatech.todomvc;

import java.util.List;

import javax.inject.Inject;

import com.studiomediatech.todomvc.domain.Todo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TodoMVCController {

  private static final String INDEX = "home";
  private static final String REDIRECT_TO_ROOT = "redirect:/";
  private static final String EDIT_TODO_MODEL = "editTodo";

  private final TodoService todoService;

  @Inject
  public TodoMVCController(TodoService todoService) {
    this.todoService = todoService;
  }

  @ModelAttribute("todos")
  public List<Todo> populateTodos() {
    return this.todoService.findAll();
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index() {
    return INDEX;
  }

  @RequestMapping(value = "/create-new", method = RequestMethod.POST)
  public String saveNewTodo(Todo todo, BindingResult bindingResult, ModelMap model) {
    this.todoService.save(todo);
    return REDIRECT_TO_ROOT;
  }

  @RequestMapping(value = "/toggle-completed", method = RequestMethod.POST)
  public String markComplete(Todo todo, BindingResult bindingResult, ModelMap model) {
    this.todoService.toggleCompleted(todo.getId());
    return REDIRECT_TO_ROOT;
  }

  @RequestMapping(value = "/edit/{id}/", method = RequestMethod.GET)
  public ModelAndView edit(@PathVariable("id") Long id) {
    Todo editTodo = this.todoService.findOne(id);
    editTodo.beginEditing();
    this.todoService.save(editTodo);
    return new ModelAndView(INDEX, EDIT_TODO_MODEL, editTodo);
  }

  @RequestMapping(value = "/save-edit", method = RequestMethod.POST)
  public ModelAndView saveEditedTodo(Todo todo, BindingResult bindingResult, ModelMap model) {
    todo.endEditing();
    this.todoService.save(todo);
    return new ModelAndView(INDEX, EDIT_TODO_MODEL, null);
  }
}
