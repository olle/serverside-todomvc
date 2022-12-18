package todomvc.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import todomvc.domain.TodoItem;

class TodoItemRepositoryImpl implements TodoItemRepository {

	private static long SEQ = 1;

	private final Map<Long, TodoItem> todos = new ConcurrentHashMap<>();

	private final AtomicBoolean showCompleted = new AtomicBoolean(true);

	@Override
	public Collection<TodoItem> fetchActive() {
		return todos.values().stream().filter(TodoItem::isActive).toList();
	}

	@Override
	public Collection<TodoItem> fetchCompleted() {
		return todos.values().stream().filter(TodoItem::isCompleted).toList();
	}

	@Override
	public void clearAllCompletedTodoItems() {
		todos.entrySet().removeIf(e -> e.getValue().isCompleted());
	}

	@Override
	public boolean isEditing() {
		return todos.values().stream().anyMatch(TodoItem::isEditing);
	}

	@Override
	public void createNewTodoItem(String todoText) {
		TodoItem newTodoItem = TodoItem.from(todoText, System.currentTimeMillis() + (SEQ++));
		todos.put(newTodoItem.getId(), newTodoItem);
	}

	private void save(TodoItem todoItem) {
		TodoItem todoItemCopy = todoItem.copy();
		todos.put(todoItemCopy.getId(), todoItemCopy);
	}

	@Override
	public void deleteById(long id) {
		todos.remove(id);
	}

	@Override
	public void markCompletedById(long id) {
		Optional.ofNullable(todos.get(id)).map(TodoItem::markCompleted).ifPresent(this::save);
	}

	@Override
	public void markActiveById(long id) {
		Optional.ofNullable(todos.get(id)).map(TodoItem::markActive).ifPresent(this::save);
	}

	@Override
	public void markEditingById(long id) {
		Optional.ofNullable(todos.get(id)).map(TodoItem::markEditing).ifPresent(this::save);
	}

	@Override
	public void updateById(long id, String todoText) {
		Optional.ofNullable(todos.get(id)).map(todoItem -> todoItem.update(todoText)).ifPresent(this::save);
	}

	@Override
	public void toggleShowCompleted() {
		this.showCompleted.set(true);
	}

	@Override
	public void toggleHideCompleted() {
		this.showCompleted.set(false);
	}

	@Override
	public boolean isShowingCompleted() {
		return showCompleted.get();
	}
}
