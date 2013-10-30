package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;
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
 * 
 * @see Storage
 */
public final class GuavaCacheStorage<T extends Identifiable<ID>, ID extends Serializable> extends Storage<T, ID> {

  private Cache<Serializable, Repository<T, ID>> repositories;
  private Callable<Repository<T, ID>> loader;

  public GuavaCacheStorage() {
    this.repositories = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
    this.loader = new Callable<Repository<T, ID>>() {

      @Override
      public Repository<T, ID> call() throws Exception {
        return new HashMapRepository<>();
      }
    };
  }

  @Override
  public Repository<T, ID> forKey(Serializable key) {
    try {
      return this.repositories.get(key, this.loader);
    }
    catch (ExecutionException e) {
      throw new IllegalStateException(e);
    }
  }
}
