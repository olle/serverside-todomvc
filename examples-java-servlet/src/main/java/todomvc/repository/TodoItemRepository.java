package todomvc.repository;

import java.util.Collection;

import todomvc.domain.TodoItem;

public interface TodoItemRepository {

	void createNewTodoItem(String todoText);

	void updateById(long id, String todoText);

	void deleteById(long id);

	void markCompletedById(long id);

	void markActiveById(long id);

	void markEditingById(long id);

	Collection<TodoItem> fetchActive();

	Collection<TodoItem> fetchCompleted();

	boolean isEditing();

	void clearAllCompletedTodoItems();

	void toggleShowCompleted();

	void toggleHideCompleted();

	boolean isShowingCompleted();
}