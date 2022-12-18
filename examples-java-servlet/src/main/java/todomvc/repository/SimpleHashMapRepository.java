package todomvc.repository;

import java.util.Collection;

import todomvc.domain.TodoItem;

public class SimpleHashMapRepository implements Repository<TodoItem, Long>{

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<TodoItem> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<TodoItem> findAll(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TodoItem findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TodoItem save(TodoItem entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<TodoItem> save(Iterable<? extends TodoItem> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(TodoItem entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends TodoItem> entities) {
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
