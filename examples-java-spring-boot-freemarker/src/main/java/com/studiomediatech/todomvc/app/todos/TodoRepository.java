package com.studiomediatech.todomvc.app.todos;

import com.studiomediatech.todomvc.app.todos.Todo.Status;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findOneById(Long id);


    List<Todo> findByEditingTrue();


    void deleteByStatus(Status status);


    List<Todo> findByStatus(Status status);
}
