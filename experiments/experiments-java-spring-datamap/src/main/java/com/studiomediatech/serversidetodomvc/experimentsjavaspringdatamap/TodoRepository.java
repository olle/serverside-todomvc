package com.studiomediatech.serversidetodomvc.experimentsjavaspringdatamap;

import java.util.Collection;

import org.springframework.util.MultiValueMap;

public interface TodoRepository {

	Collection<Todo> findAll();

	void saveTodo(MultiValueMap<String, Object> data);

}
