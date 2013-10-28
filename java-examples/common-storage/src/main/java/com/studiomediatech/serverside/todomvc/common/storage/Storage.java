package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;

/**
 * Interface that defines a storage that can provide a specific repository based
 * on a key token.
 * 
 * @author Olle Törnström <olle@studiomediatech.com>
 * 
 * @param <K> type of the repository identifier key
 * 
 * @see Identifiable
 * @see Repository
 */
public interface Storage<K extends Serializable> {

  /**
   * Returns the repository specified by the the given key.
   * 
   * @param key identifying the repository
   * 
   * @return a repository instance, never {@code null}
   */
  <T extends Identifiable<ID>, ID extends Serializable> Repository<V<T>, ID> forKey(K key);
}