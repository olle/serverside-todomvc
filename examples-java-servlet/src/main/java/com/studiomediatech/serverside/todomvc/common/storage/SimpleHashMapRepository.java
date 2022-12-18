package com.studiomediatech.serverside.todomvc.common.storage;

import java.util.Collection;

import com.studiomediatech.serverside.todomvc.servlet.Todo;

public class SimpleHashMapRepository implements Repository<Todo, Long>{

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Todo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Todo> findAll(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Todo findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Todo save(Todo entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Todo> save(Iterable<? extends Todo> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Todo entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Todo> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
