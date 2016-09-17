package com.studiomediatech.todomvc.todos;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest {

	@Mock
	private TodoService todoService;
	
	@Captor
	private ArgumentCaptor<Todo> todoCaptor;
	
	@Test
	public void ensurePostToTodosCollectsDataAndCallsSaveTodo() throws Exception {
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new TodoController(todoService)).build();
		
		mockMvc.perform(post("/todos/").param("item-text", "todo text")).andExpect(status().isOk());
		
		verify(todoService).saveTodo(todoCaptor.capture());		
		Todo todo = todoCaptor.getValue();
		
		assertThat(todo, is(instanceOf(Todo.class)));
		assertThat(todo.getText(), is("todo text"));
		assertThat(todo.getId(), is(nullValue()));
	}

}
