package com.studiomediatech.serversidetodomvc.experimentsjavaspringdatamap;

import java.util.Map;

interface Todo {
	
	String getId();
	String getText();
}

class TodoImpl implements Todo {
	
	private final Map<String, Object> data;

	public TodoImpl(Map<String, Object> data) {
		this.data = data;
	}

	@Override
	public String getId() {
		return "" + data.hashCode();
	}

	@Override
	public String getText() {
		return (String) data.get("text");
	}
}