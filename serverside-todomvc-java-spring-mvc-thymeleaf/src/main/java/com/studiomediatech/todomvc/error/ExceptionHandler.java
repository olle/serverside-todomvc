package com.studiomediatech.todomvc.error;

import com.google.common.base.Throwables;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandler {

  /**
   * Handle exceptions thrown by handlers.
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
  public ModelAndView exception(Exception exception, WebRequest request) {
    ModelAndView modelAndView = new ModelAndView("error/general");
    modelAndView.addObject("errorMessage", Throwables.getRootCause(exception));
    return modelAndView;
  }
}