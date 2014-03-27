package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class Storage<T extends Identifiable<ID>, ID extends Serializable> {

  /**
   * Returns the store specified by the the given key.
   * 
   * @param key identifying the repository
   * 
   * @return a repository instance, never {@code null}
   */
  public abstract Repository<T, ID> forKey(Serializable key);

  /**
   * Returns a new empty storage.
   */
  public static <T extends Identifiable<ID>, ID extends Serializable> Storage<T, ID> newGuavaCacheStorage() {
    return new GuavaCacheStorage<>();
  }

  /**
   * Returns a new storage with the given {@code value} as the initial stored
   * state.
   */
  public static <T extends Identifiable<ID>, ID extends Serializable> Storage<T, ID> newGuavaCacheStorage(T value) {
    return newGuavaCacheStorage(Arrays.asList(value));
  }

  /**
   * Returns a new storage with the given list of {@code values} as the initial
   * stored state.
   */
  public static <T extends Identifiable<ID>, ID extends Serializable> Storage<T, ID> newGuavaCacheStorage(List<T> values) {
    return new GuavaCacheStorage<>(values);
  }
}
