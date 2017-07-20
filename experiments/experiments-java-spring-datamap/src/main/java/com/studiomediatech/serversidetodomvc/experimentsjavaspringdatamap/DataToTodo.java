package com.studiomediatech.serversidetodomvc.experimentsjavaspringdatamap;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DataToTodo implements Converter<Map<String, Object>, Todo>, Loggable {

	@Override
	public Todo convert(Map<String, Object> data) {
		logger().debug("Converting from {} to todo...", data);
		// Type overlay only!
		Todo todo = new Todo() {
			@Override
			public String getText() {
				return (String) data.get("text");
			}

			@Override
			public String getId() {
				return (String) data.get("id");
			}
		};
		logger().debug("Conversion complete into {}", todo);
		return todo;
	}

}
