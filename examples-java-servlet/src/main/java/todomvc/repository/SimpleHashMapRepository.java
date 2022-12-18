package todomvc.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import todomvc.domain.TodoItem;

public class SimpleHashMapRepository implements Repository<TodoItem, Long> {

	private static long SEQ = 1;

	private final Map<Long, TodoItem> todos = new ConcurrentHashMap<>();

	@Override
	public Collection<TodoItem> findAllActive() {
		return todos.values().stream().filter(TodoItem::isActive).toList();
	}

	@Override
	public Collection<TodoItem> findAllCompleted() {
		return todos.values().stream().filter(TodoItem::isCompleted).toList();
	}

	@Override
	public void clearAllCompletedTodoItems() {
		todos.entrySet().removeIf(e -> e.getValue().isCompleted());
	}

	@Override
	public void save(TodoItem entity) {

		final TodoItem copy;

		if (entity.isNew()) {
			copy = TodoItem.from(entity, System.currentTimeMillis() + (SEQ++));
		} else {
			copy = TodoItem.from(entity);
		}

		todos.put(copy.getId(), copy);
	}

	@Override
	public void deleteById(String todoId) {
		todos.remove(Long.parseLong(todoId));
	}

	@Override
	public void markCompletedById(long id) {
		Optional.ofNullable(todos.get(id)).map(TodoItem::markCompleted).ifPresent(this::save);
	}

	@Override
	public void markActiveById(long id) {
		Optional.ofNullable(todos.get(id)).map(TodoItem::markActive).ifPresent(this::save);
	}
}
