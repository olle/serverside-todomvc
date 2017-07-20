package com.studiomediatech.serversidetodomvc.experimentsjavaspringdatamap;

import java.util.Collection;
import java.util.Map;

interface TodoService {

	Collection<Todo> getAllTodos();

	void addNewTodo(Map<String, Object> data);
}