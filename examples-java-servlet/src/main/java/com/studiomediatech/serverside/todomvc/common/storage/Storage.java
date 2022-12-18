package com.studiomediatech.serverside.todomvc.common.storage;

import com.studiomediatech.serverside.todomvc.servlet.Todo;

public interface Storage<T1, T2> {

	Repository<Todo, Long> forKey(String sessionId);

}
