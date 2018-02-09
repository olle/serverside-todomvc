package com.studiomediatech.todomvc.web;

import com.studiomediatech.todomvc.app.todos.Todo;
import com.studiomediatech.todomvc.app.todos.TodoService;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;


@Controller
class TodoController {

    private static final String VIEW = "todos";
    private static final String TODOS_ATTR = "todos";
    private static final String REDIRECT_TO_INDEX = "redirect:/";

    private final TodoService todoService;

    public TodoController(TodoService todoService) {

        this.todoService = todoService;
    }

    @GetMapping("/")
    public String index(Model model) {

        return filtered(model, "all");
    }


    @GetMapping(path = "/", params = { "filter" })
    public String filtered(Model model,
        @RequestParam(name = "filter", defaultValue = "all") String filter) {

        model.addAttribute("filter", filter);

        if ("all".equals(filter)) {
            model.addAttribute(TODOS_ATTR, convert(todoService.getTodos()));
        } else if ("active".equals(filter)) {
            model.addAttribute(TODOS_ATTR, convert(todoService.getActive()));
        } else if ("completed".equals(filter)) {
            model.addAttribute(TODOS_ATTR, convert(todoService.getCompleted()));
        }

        return VIEW;
    }


    @GetMapping(path = "/", params = { "action", "item-id" })
    public String editTodo(@RequestParam("item-id") Long id) {

        todoService.editTodo(id);

        return REDIRECT_TO_INDEX;
    }


    @PostMapping("/todos")
    public String newTodo(@RequestParam("item-text") String itemText) {

        Todo todo = new Todo(itemText);
        todoService.saveTodo(todo);

        return REDIRECT_TO_INDEX;
    }


    @DeleteMapping(path = "/todos", params = { "filter=completed" })
    public String clearCompleted() {

        todoService.clearCompleted();

        return REDIRECT_TO_INDEX;
    }


    @PostMapping(path = "/todos/toggle", params = { "filter=completed" })
    public String markAllCompleted() {

        todoService.markAllCompleted();

        return REDIRECT_TO_INDEX;
    }


    @DeleteMapping("/todos/{id}")
    public String deleteTodo(@PathVariable Long id) {

        todoService.deleteTodo(id);

        return REDIRECT_TO_INDEX;
    }


    @PostMapping("/todos/{id}")
    public String editTodo(@PathVariable Long id,
        @RequestParam("item-text") String itemText) {

        todoService.updateTodo(id, itemText);

        return REDIRECT_TO_INDEX;
    }


    @PostMapping("/todos/{id}/status")
    public String toggleStatus(@PathVariable Long id) {

        todoService.toggleStatus(id);

        return REDIRECT_TO_INDEX;
    }


    private List<TodoDto> convert(List<Todo> todos) {

        return todos.stream().map(TodoDto::new).collect(Collectors.toList());
    }
}
