package controllers;

import java.util.Arrays;
import java.util.List;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.session.Session;

public class TodoFilter implements Filter {

	private static final String FILTER_PROPERTY = "filter";

	private static final String ALL = "all";
	private static final String COMPLETED = "completed";
	private static final String ACTIVE = "active";

	private static final List<String> FILTERS = Arrays.asList(ALL, ACTIVE, COMPLETED);

	@Override
	public Result filter(FilterChain chain, final Context context) {

		String val = context.getParameter(FILTER_PROPERTY);

		String filter = FILTERS.stream().filter(name -> name.equals(val)).findFirst().orElse(ALL);
		Session session = context.getSession();
		session.put(FILTER_PROPERTY, filter);

		// Always chains
		return chain.next(context);
	}
}
