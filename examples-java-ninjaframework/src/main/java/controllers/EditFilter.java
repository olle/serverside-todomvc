package controllers;

import com.google.common.base.Optional;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

public class EditFilter implements Filter {

  @Override
  public Result filter(FilterChain chain, Context context) {

    String edit = context.getParameter("edit");
    context.getSession().put("edit", Optional.fromNullable(edit).or(""));
    return chain.next(context);
  }

}
