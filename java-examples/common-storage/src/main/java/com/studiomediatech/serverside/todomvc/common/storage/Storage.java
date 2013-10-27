package com.studiomediatech.serverside.todomvc.common.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;

public class Storage<T extends Identifiable> {

  private volatile Cache<String, List<T>> storages;
  private volatile Callable<List<T>> defaults;

  private List<T> storage;

  public Storage() {
    this(new Callable<List<T>>() {

      @Override
      public List<T> call() throws Exception {
        return new ArrayList<T>();
      }
    });
  }

  public Storage(Callable<List<T>> defaults) {
    this.defaults = defaults;
    this.storages = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
  }

  private Storage(List<T> storage) {
    this.storage = storage;
  }

  public Storage<T> forKey(String key) {
    try {
      List<T> storage = this.storages.get(key, this.defaults);
      return new Storage<T>(storage);
    }
    catch (ExecutionException e) {
      throw new IllegalStateException(e);
    }
  }

  // CRUD

  public Iterable<T> findAll() {
    assertStorageFoundByKey();
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return Storage.this.storage.iterator();
      }
    };
  }

  public T save(T entity) {
    assertStorageFoundByKey();
    T removed = entity;
    while (this.storage.contains(entity)) {
      removed = this.storage.remove(this.storage.indexOf(entity));
    }
    // entity.setId(Integer.toString(entity.hashCode()));
    this.storage.add(entity);
    return removed;
  }

  public void delete(T entity) {
    assertStorageFoundByKey();
    while (this.storage.contains(entity)) {
      this.storage.remove(entity);
    }
  }

  public void delete(Iterable<? extends T> entities) {
    assertStorageFoundByKey();
    this.storage.removeAll(Lists.newArrayList(entities));
  }

  // void delete(ID id)
  // Deletes the entity with the given id.

  // void deleteAll()
  // Deletes all entities managed by the repository.

  // boolean exists(ID id)
  // Returns whether an entity with the given id exists.

  // T findOne(ID id)
  // Retrives an entity by its primary key.

  // Iterable<T> save(Iterable<? extends T> entities)
  // Saves all given entities.

  private void assertStorageFoundByKey() {
    try {
      this.storage.hashCode();
    }
    catch (NullPointerException e) {
      throw new IllegalStateException("Storage must be selected using `forKey' before issuing CRUD operations.");
    }
  }

}
