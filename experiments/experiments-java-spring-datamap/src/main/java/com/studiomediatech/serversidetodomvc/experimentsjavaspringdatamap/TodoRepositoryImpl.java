package com.studiomediatech.serversidetodomvc.experimentsjavaspringdatamap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

@Repository
public class TodoRepositoryImpl implements TodoRepository, Loggable {

	// No thread-safety what so ever!
	private final Map<String, Map<String, Object>> data = new HashMap<>();

	private final Converter<Map<String, Object>, Todo> toTodo;

	public TodoRepositoryImpl(Converter<Map<String, Object>, Todo> toTodo) {
		this.toTodo = toTodo;
	}

	@Override
	public Collection<Todo> findAll() {
		logger().debug("Reading todos from data {}", data);
		return data.values().stream().map(toTodo::convert).collect(Collectors.toSet());
	}

	@Override
	public void saveTodo(MultiValueMap<String, Object> data) {
		String id = "" + data.hashCode();
		this.data.put(id, data.toSingleValueMap());
		logger().debug("Stored new todo under id {}, {}", id, data);
	}
}
