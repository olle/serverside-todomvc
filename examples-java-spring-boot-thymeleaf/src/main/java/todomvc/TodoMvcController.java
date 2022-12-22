package todomvc;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TodoMvcController {

    private static final String INDEX_VIEW = "index";
    private static final String REDIRECT_ROOT = "redirect:/";

    private final TodoMvcService service;

    public TodoMvcController(TodoMvcService service) {

        this.service = service;
    }

    @GetMapping("/")
    public String showTodos(Model model) {

        model.mergeAttributes(service.showTodos());

        return INDEX_VIEW;
    }


    @PostMapping(path = "/todos", params = { "todo" })
    public String addNewTodo(String todo) {

        service.addNewTodo(todo);

        return REDIRECT_ROOT;
    }


    @PostMapping(path = "/todo", params = { "complete" })
    public String completeTodo(String complete) {

        service.completeTodo(complete);

        return REDIRECT_ROOT;
    }


    @PostMapping(path = "/controls", params = { "hide" })
    public String hideCompleted() {

        service.hideCompleted();

        return REDIRECT_ROOT;
    }


    @PostMapping(path = "/controls", params = { "show" })
    public String showCompleted() {

        service.showCompleted();

        return REDIRECT_ROOT;
    }


    @PostMapping(path = "/controls", params = { "clear" })
    public String clearCompleted() {

        service.clearCompleted();

        return REDIRECT_ROOT;
    }
}
