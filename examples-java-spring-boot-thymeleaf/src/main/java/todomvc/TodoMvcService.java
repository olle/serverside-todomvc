package todomvc;

import org.springframework.stereotype.Service;

import todomvc.Todo.Status;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class TodoMvcService {

    private final TodoMvcRepository repo;

    private final AtomicBoolean hidden = new AtomicBoolean(false);

    public TodoMvcService(TodoMvcRepository repo) {

        this.repo = repo;
    }

    public Map<String, ?> showTodos() {

        return Map.of( // NOSONAR
                "hidden", hidden.get(), // NOSONAR
                "active", repo.findAllByStatus(Status.ACTIVE), // NOSONAR
                "completed", repo.findAllByStatus(Status.COMPLETED));
    }


    public void addNewTodo(String todo) {

        repo.save(Todo.newFrom(todo));
    }


    public void completeTodo(String uuid) {

        repo.findOneById(UUID.fromString(uuid)).map(Todo::markAsCompleted).ifPresent(repo::save);
    }


    public void hideCompleted() {

        hidden.set(true);
    }


    public void showCompleted() {

        hidden.set(false);
    }


    public void clearCompleted() {

        repo.deleteByStatus(Status.COMPLETED);
    }
}
