package todomvc.repository;

import java.io.Serializable;
import java.util.Collection;

import todomvc.domain.TodoItem;

public interface Repository<T extends Identifiable<ID>, ID extends Serializable> {

	void createNewTodoItem(String todoText);

	void updateTodoItem(long id, String todoText);

	void deleteById(long id);

	void markCompletedById(long id);

	void markActiveById(long id);

	void markEditingById(long id);

	Collection<TodoItem> fetchActive();

	Collection<TodoItem> fetchCompleted();

	boolean isEditing();

	void clearAllCompletedTodoItems();
}