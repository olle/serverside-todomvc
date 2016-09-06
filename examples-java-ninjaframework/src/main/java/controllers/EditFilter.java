package controllers;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

public class EditFilter implements Filter {

  @Override
  public Result filter(FilterChain chain, Context context) {

    String edit = context.getParameter("edit");
    context.getSession().put("edit", java.util.Optional.ofNullable(edit).orElse(""));
    return chain.next(context);
  }

}
