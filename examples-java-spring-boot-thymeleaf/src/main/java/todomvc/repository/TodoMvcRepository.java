package todomvc.repository;

import org.springframework.stereotype.Repository;

import todomvc.domain.Todo;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public final class TodoMvcRepository {

    private final Map<UUID, Todo> todos = new ConcurrentHashMap<>();

    public Collection<Todo> findAllWhereActiveIsTrue() {

        return todos.values().stream().filter(Todo::isActive).toList();
    }


    public void save(Todo todo) {

        var copy = todo.copy();
        todos.put(copy.getId(), copy);
    }
}
