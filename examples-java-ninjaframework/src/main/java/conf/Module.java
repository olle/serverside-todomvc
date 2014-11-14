package conf;

import service.InMemoryTodoService;
import service.TodoService;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

@Singleton
public class Module extends AbstractModule {

  @Override
  protected void configure() {

    bind(TodoService.class).to(InMemoryTodoService.class);
  }
}
