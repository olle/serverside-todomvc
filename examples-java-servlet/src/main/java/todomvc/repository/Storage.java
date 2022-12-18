package todomvc.repository;

import todomvc.domain.TodoItem;

public interface Storage<T1, T2> {

	Repository<TodoItem, Long> forKey(String sessionId);

}
