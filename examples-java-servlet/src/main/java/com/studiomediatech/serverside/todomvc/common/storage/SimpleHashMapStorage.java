package com.studiomediatech.serverside.todomvc.common.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.studiomediatech.serverside.todomvc.servlet.Todo;

public class SimpleHashMapStorage implements Storage<Todo, Long> {

	private final Map<String, Repository<Todo, Long>> repos = new ConcurrentHashMap<>();
	
	@Override
	public Repository<Todo, Long> forKey(String sessionId) {
		return repos.computeIfAbsent(sessionId, k -> new SimpleHashMapRepository());
	}

}
