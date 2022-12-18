package todomvc.repository;

import java.io.Serializable;
import java.util.Collection;

import todomvc.domain.TodoItem;

public interface Repository<T extends Identifiable<ID>, ID extends Serializable> {

	T save(T entity);

	void markCompletedById(long parseLong);

	void markActiveById(long parseLong);

	Collection<TodoItem> findAllActive();

	Collection<TodoItem> findAllCompleted();

}