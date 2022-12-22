package todomvc;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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


    @PostMapping(path = "/todos/{id}", params = { "update" })
    public String updateTodo(@PathVariable("id") String id,
        @RequestParam("update") String update) {

        service.updateTodo(id, update);

        return REDIRECT_ROOT;
    }


    @PostMapping(path = "/todo", params = { "complete" })
    public String completeTodo(String complete) {

        service.completeTodo(complete);

        return REDIRECT_ROOT;
    }


    @PostMapping(path = "/todo", params = { "revert" })
    public String activateTodo(String revert) {

        service.activateTodo(revert);

        return REDIRECT_ROOT;
    }


    @PostMapping(path = "/todo", params = { "edit" })
    public String editTodo(String edit) {

        service.editTodo(edit);

        return REDIRECT_ROOT;
    }


    @PostMapping(path = "/todo", params = { "delete" })
    public String deleteTodo(String delete) {

        service.deleteTodo(delete);

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
