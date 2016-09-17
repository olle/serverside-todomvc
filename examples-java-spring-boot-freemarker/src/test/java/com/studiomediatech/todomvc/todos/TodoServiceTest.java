package com.studiomediatech.todomvc.todos;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

	@Mock
	private TodoRepository todoRepository;
	
	
	@Test
	public void ensureSaveTodoDelegatesToRepository() {
		
		TodoService service = new TodoService(todoRepository);
		Todo todo = new Todo("text");
		service.saveTodo(todo);
		verify(todoRepository).save(todo);		
	}

}
