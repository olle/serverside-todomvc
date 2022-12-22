package todomvc;

import org.springframework.stereotype.Service;

import todomvc.Todo.Status;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;


@Service
public class TodoMvcService {

    private final TodoMvcRepository repo;

    private final AtomicBoolean hidden = new AtomicBoolean(false);

    public TodoMvcService(TodoMvcRepository repo) {

        this.repo = repo;
    }

    public Map<String, ?> showTodos() {

        return Map.of( // NOSONAR
                "notEditing", repo.existsWhereIsEditingIsTrue(), // NOSONAR
                "hidden", hidden.get(), // NOSONAR
                "active", repo.findAllByStatus(Status.ACTIVE), // NOSONAR
                "completed", repo.findAllByStatus(Status.COMPLETED));
    }


    public void addNewTodo(String todo) {

        repo.save(Todo.newFrom(todo));
    }


    public void completeTodo(String id) {

        updateWith(id, Todo::markAsCompleted);
    }


    public void activateTodo(String id) {

        updateWith(id, Todo::markAsActive);
    }


    public void editTodo(String id) {

        updateWith(id, Todo::markAsEditing);
    }


    public void updateTodo(String id, String update) {

        updateWith(id, todo -> todo.update(update));
    }


    private void updateWith(String id, UnaryOperator<Todo> mapper) {

        repo.findOneById(UUID.fromString(id)).map(mapper).ifPresent(repo::save);
    }


    public void deleteTodo(String id) {

        repo.deleteById(UUID.fromString(id));
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
