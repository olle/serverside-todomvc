package todomvc.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import todomvc.domain.TodoItem;

public class SimpleHashMapStorage implements Storage<TodoItem, Long> {

	private final Map<String, Repository<TodoItem, Long>> repos = new ConcurrentHashMap<>();
	
	@Override
	public Repository<TodoItem, Long> forKey(String sessionId) {
		return repos.computeIfAbsent(sessionId, k -> new SimpleHashMapRepository());
	}

}
