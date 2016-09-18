package com.studiomediatech.todomvc.todos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(MockitoJUnitRunner.class)
public class AllTodosHandlerInterceptorTest {

	@Mock
	private TodoService todoService;
	
	@Test
	public void ensurePostHandlerFetchesAllTodos() throws Exception {
		
		AllTodosHandlerInterceptor interceptor = new AllTodosHandlerInterceptor(todoService);
		
		ModelAndView modelAndView = new ModelAndView();
		
		assertNull(modelAndView.getModelMap().get("todos"));
		
		interceptor.postHandle(null, null, null, modelAndView);
		
		assertNotNull("Missing property `todos`", modelAndView.getModelMap().get("todos"));
		
		verify(todoService).getTodos();
	}

}
