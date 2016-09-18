package com.studiomediatech.todomvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class TodoMVCConfiguration {
	
	@Configuration
	public static class Interceptors extends WebMvcConfigurerAdapter {
		
		@Autowired
		@Qualifier("allTodosHandlerInterceptor")
		private HandlerInterceptor allTodosHandlerInterceptor;
		
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
		
			registry.addInterceptor(allTodosHandlerInterceptor).addPathPatterns("/**");
		}
	}

}
