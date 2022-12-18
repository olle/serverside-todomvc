package todomvc.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Storage {

	static final Map<String, TodoItemRepository> REPOSITORIES = new ConcurrentHashMap<>();

	default TodoItemRepository forKey(String sessionId) {
		return REPOSITORIES.computeIfAbsent(sessionId, k -> new TodoItemRepositoryImpl());
	}

	static Storage create() {

		return new Storage() {
			// OK
		};
	}
}
