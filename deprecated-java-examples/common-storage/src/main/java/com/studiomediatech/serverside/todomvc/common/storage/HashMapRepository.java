package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

/**
 * A shameless map based repository implementation, for just the simplest
 * possible in-memory storage solution - ever.
 * 
 * @author Olle Törnström <olle@studiomediatech.com>
 */
public final class HashMapRepository<T extends Identifiable<ID>, ID extends Serializable> implements Repository<T, ID> {

  private final HashMap<ID, T> repository;

  public HashMapRepository() {
    this(Collections.<T> emptyList());
  }

  public HashMapRepository(Collection<T> values) {
    this.repository = new HashMap<>();
    for (T value : values) {
      this.repository.put(value.getId(), value);
    }
  }

  @Override
  public long count() {
    return this.repository.size();
  }

  @Override
  public Iterable<T> findAll() {
    if (this.repository.isEmpty()) {
      return null;
    }
    else {
      return Lists.newArrayList(this.repository.values());
    }
  }

  @Override
  public Iterable<T> findAll(Iterable<ID> ids) {
    Collection<T> results = new ArrayList<>();
    for (ID id : ids) {
      if (this.repository.containsKey(id)) {
        results.add(this.repository.get(id));
      }
    }
    if (results.isEmpty()) {
      return null;
    }
    else {
      return results;
    }
  }

  @Override
  public T findOne(ID id) {
    return this.repository.get(id);
  }

  @Override
  public T save(T entity) {
    this.repository.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public Iterable<T> save(Iterable<? extends T> entities) {
    Collection<T> results = new ArrayList<>();
    for (T entity : entities) {
      results.add(save(entity));
    }
    return results;
  }

  @Override
  public void delete(T entity) {
    if (this.repository.containsValue(entity)) {
      Iterator<Entry<ID, T>> entries = this.repository.entrySet().iterator();
      while (entries.hasNext()) {
        Entry<ID, T> entry = entries.next();
        if (entry.getValue().equals(entity)) {
          entries.remove();
        }
      }
    }
  }

  @Override
  public void delete(ID id) {
    this.repository.remove(id);
  }

  @Override
  public void delete(Iterable<? extends T> entities) {
    for (T entity : entities) {
      delete(entity);
    }
  }

  @Override
  public void deleteAll() {
    this.repository.clear();
  }

  @Override
  public boolean exists(ID id) {
    return this.repository.containsKey(id);
  }

}
