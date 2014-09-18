package com.studiomediatech.todomvc.config;

import com.studiomediatech.todomvc.TodoMVC;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackageClasses = TodoMVC.class, excludeFilters = @Filter({ Controller.class,
                                                                                 Configuration.class }))
class TodoMVCConfig {
  // Ok
}