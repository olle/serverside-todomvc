package todomvc;

import org.springframework.stereotype.Service;

import todomvc.domain.Todo;

import todomvc.repository.TodoMvcRepository;

import java.util.Collection;
import java.util.Map;


@Service
public class TodoMvcService {

    private final TodoMvcRepository repo;

    public TodoMvcService(TodoMvcRepository repo) {

        this.repo = repo;
    }

    public Map<String, ?> showTodos() {

        Collection<Todo> activeTodos = repo.findAllWhereActiveIsTrue();

        return Map.of("active", activeTodos);
    }


    public void addNewTodo(String todo) {

        repo.save(Todo.newFrom(todo));
    }
}
