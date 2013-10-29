package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;

public abstract class Storage<T extends Identifiable<ID>, ID extends Serializable> implements Repository<T, ID> {

  /**
   * Returns the store specified by the the given key.
   * 
   * @param key identifying the repository
   * 
   * @return a repository instance, never {@code null}
   */
  public abstract Repository<T, ID> forKey(Serializable key);

  public static <T extends Identifiable<ID>, ID extends Serializable> Storage<T, ID> newGuavaCacheStorage() {
    return new GuavaCacheStorage<>();
  }
}
