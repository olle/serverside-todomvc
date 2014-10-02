package com.studiomediatech.todomvc;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.*;

import static spark.SparkBase.*;

public class TodoMVC {

  public static void main(String[] args) {

    staticFileLocation("/public");

    Map<String, Object> map = new HashMap<>();
    map.put("foo", "bar");

    get("/", (req, res) -> new ModelAndView(map, "index.mustache"), new MustacheTemplateEngine());
  }
}
