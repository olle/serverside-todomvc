package todomvc;

import org.springframework.stereotype.Repository;

import todomvc.Todo.Status;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public final class TodoMvcRepository {

    private final Map<UUID, Todo> todos = new ConcurrentHashMap<>();

    public void save(Todo todo) {

        var copy = todo.copy();
        todos.put(copy.getId(), copy);
    }


    public Optional<Todo> findOneById(UUID id) {

        return Optional.ofNullable(todos.get(id));
    }


    public Collection<Todo> findAllByStatus(Status status) {

        return todos.values().stream().filter(todo -> todo.getStatus() == status).toList();
    }


    public void deleteByStatus(Status status) {

        todos.entrySet().removeIf(e -> e.getValue().getStatus() == status);
    }


    public boolean existsWhereIsEditingIsTrue() {

        return todos.values().stream().noneMatch(Todo::isEditing);
    }


    public void deleteById(UUID id) {

        todos.entrySet().removeIf(e -> e.getKey().equals(id));
    }
}
