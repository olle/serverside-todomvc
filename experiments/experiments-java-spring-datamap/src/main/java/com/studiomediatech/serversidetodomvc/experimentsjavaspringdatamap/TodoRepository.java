package com.studiomediatech.serversidetodomvc.experimentsjavaspringdatamap;

import java.util.Collection;
import java.util.Map;

public interface TodoRepository {

	Collection<Todo> findAll();

	void saveTodo(Map<String, Object> data);

}
