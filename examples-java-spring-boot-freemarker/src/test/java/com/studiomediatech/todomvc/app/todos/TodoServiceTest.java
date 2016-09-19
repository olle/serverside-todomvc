package com.studiomediatech.todomvc.app.todos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

	@Mock
	private TodoRepository todoRepository;
	
	private TodoService service;
	
	@Before
	public void setup() {
		
		service = new TodoService(todoRepository);
	}
	
	@Test
	public void ensureSaveDelegatesToRepository() {
		
		Todo todo = new Todo("Hello World!");
		service.saveTodo(todo);
		verify(todoRepository).save(todo);
	}
	
	@Test
	public void ensureDelegatesToFindAll() throws Exception {
		
		service.getTodos();
		verify(todoRepository).findAll();
		
	}
	
	@Test
	public void ensureDeleteDelegatesToRepository() throws Exception {
		
		service.deleteTodo(123L);
		verify(todoRepository).delete(123L);
		
	}

}
