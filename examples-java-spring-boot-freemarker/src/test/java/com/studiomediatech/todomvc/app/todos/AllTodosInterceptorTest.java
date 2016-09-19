package com.studiomediatech.todomvc.app.todos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.studiomediatech.todomvc.app.todos.AllTodosInterceptor;

@RunWith(MockitoJUnitRunner.class)
public class AllTodosInterceptorTest {

	@Mock
	private TodoService todoService;
	
	@Test
	public void ensurePostHandlerFetchesAllTodos() throws Exception {
		
		AllTodosInterceptor interceptor = new AllTodosInterceptor(todoService);
		
		ModelAndView modelAndView = new ModelAndView();
		
		assertNull(modelAndView.getModelMap().get("todos"));
		
		interceptor.postHandle(null, null, null, modelAndView);
		
		assertNotNull("Missing property `todos`", modelAndView.getModelMap().get("todos"));
		
		verify(todoService).getTodos();
	}

}
