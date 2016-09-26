package com.studiomediatech.todomvc.app.todos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studiomediatech.todomvc.app.todos.Todo.Status;

public interface TodoRepository extends JpaRepository<Todo, Long>{

	Optional<Todo> findOneById(Long id);
	
	List<Todo> findByEditingTrue();

	void deleteByStatus(Status status);
}
