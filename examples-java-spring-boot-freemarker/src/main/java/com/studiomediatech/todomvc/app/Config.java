package com.studiomediatech.todomvc.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class Config {

	@Configuration
	public static class Interceptors extends WebMvcConfigurerAdapter {

		@Autowired
		@Qualifier("allTodosInterceptor")
		private HandlerInterceptor allTodosInterceptor;

		@Override
		public void addInterceptors(InterceptorRegistry registry) {

			registry.addInterceptor(allTodosInterceptor).addPathPatterns("/**");
		}
	}
}
