package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * A storage implementation that uses Google guava's cache to store entities for
 * a short time only.
 * 
 * <p>
 * This is a sweet little storage implementation that provides a quick way to
 * store items and ensure that they are fully cleared out after a short while.
 * <strong>Just don't use this for production purposes please.</strong>
 * </p>
 * 
 * @author Olle Törnström <olle@studiomediatech.com>
 */
public class GuavaCacheStore<K extends Serializable, T extends Identifiable<ID>, ID extends Serializable> implements Storage<K> {

  private Cache<K, Collection<T>> repositories;

  private Collection<T> store;

  public GuavaCacheStore() {
    this.repositories = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
  }

  private GuavaCacheStore(Collection<T> store) {
    this.store = store;
  }

  @Override
  public <T extends Identifiable<ID>, ID extends Serializable> Repository<T, ID> forKey(K key) {
    try {
      Callable<Collection<T>> loadAsEmptyByDefault = new Callable<Collection<T>>() {
        @Override
        public Collection<T> call() throws Exception {
          return new ArrayList<>();
        }
      };
      this.repositories.get(key, loadAsEmptyByDefault);
      return new GuavaCacheStore<>(repositoryForKey);
    }
    catch (ExecutionException e) {
      throw new IllegalStateException(e);
    }
  }

  // // CRUD
  //
  // /*
  // * (non-Javadoc)
  // * @see
  // *
  // com.studiomediatech.serverside.todomvc.common.storage.CrudRepository#findAll
  // * ()
  // */
  // @Override
  // public Iterable<T> findAll() {
  // assertStorageFoundByKey();
  // return new Iterable<T>() {
  // @Override
  // public Iterator<T> iterator() {
  // return GuavaCacheStore.this.storage.iterator();
  // }
  // };
  // }
  //
  // /*
  // * (non-Javadoc)
  // * @see
  // * com.studiomediatech.serverside.todomvc.common.storage.CrudRepository#save
  // * (T)
  // */
  // @Override
  // public T save(T entity) {
  // assertStorageFoundByKey();
  // T removed = entity;
  // while (this.storage.contains(entity)) {
  // // removed = this.storage.remove(this.storage.(entity));
  // }
  // // entity.setId(Integer.toString(entity.hashCode()));
  // this.storage.add(entity);
  // return removed;
  // }
  //
  // /*
  // * (non-Javadoc)
  // * @see
  // *
  // com.studiomediatech.serverside.todomvc.common.storage.CrudRepository#delete
  // * (T)
  // */
  // @Override
  // public void delete(T entity) {
  // assertStorageFoundByKey();
  // while (this.storage.contains(entity)) {
  // this.storage.remove(entity);
  // }
  // }
  //
  // /*
  // * (non-Javadoc)
  // * @see
  // *
  // com.studiomediatech.serverside.todomvc.common.storage.CrudRepository#delete
  // * (java.lang.Iterable)
  // */
  // @Override
  // public void delete(Iterable<? extends T> entities) {
  // assertStorageFoundByKey();
  // this.storage.removeAll(Lists.newArrayList(entities));
  // }
  //
  // private void assertStorageFoundByKey() {
  // try {
  // this.storage.hashCode();
  // }
  // catch (NullPointerException e) {
  // throw new
  // IllegalStateException("Storage must be selected using `forKey' before issuing CRUD operations.");
  // }
  // }

}
