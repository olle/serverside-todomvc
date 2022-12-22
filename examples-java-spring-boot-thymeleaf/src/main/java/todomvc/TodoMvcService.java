package todomvc;

import org.springframework.stereotype.Service;

import todomvc.domain.Todo;

import todomvc.domain.Todo.Status;

import todomvc.repository.TodoMvcRepository;

import java.util.Map;
import java.util.UUID;


@Service
public class TodoMvcService {

    private final TodoMvcRepository repo;

    public TodoMvcService(TodoMvcRepository repo) {

        this.repo = repo;
    }

    public Map<String, ?> showTodos() {

        return Map.of( // NOSONAR
                "active", repo.findAllByStatus(Status.ACTIVE), // NOSONAR
                "completed", repo.findAllByStatus(Status.COMPLETED));
    }


    public void addNewTodo(String todo) {

        repo.save(Todo.newFrom(todo));
    }


    public void completeTodo(String uuid) {

        repo.findOneById(UUID.fromString(uuid))
            .map(Todo::markAsCompleted)
            .ifPresent(repo::save);
    }
}
