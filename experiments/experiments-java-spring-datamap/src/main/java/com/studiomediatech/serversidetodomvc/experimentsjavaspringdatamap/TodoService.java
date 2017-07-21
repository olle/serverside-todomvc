package com.studiomediatech.serversidetodomvc.experimentsjavaspringdatamap;

import java.util.Collection;

import org.springframework.util.MultiValueMap;

interface TodoService {

	Collection<Todo> getAllTodos();

	void addNewTodo(MultiValueMap<String, Object> data);
}