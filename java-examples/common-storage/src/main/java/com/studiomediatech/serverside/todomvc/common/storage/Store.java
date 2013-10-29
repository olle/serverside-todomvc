package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;

/**
 * Interface that defines a storage that can provide a specific repository based
 * on a key token.
 * 
 * @author Olle Törnström <olle@studiomediatech.com>
 */
public interface Store<T extends Identifiable<ID>, ID extends Serializable> extends Repository<T, ID> {

  /**
   * Returns the store specified by the the given key.
   * 
   * @param key identifying the repository
   * 
   * @return a repository instance, never {@code null}
   */
  Repository<T, ID> forKey(Serializable key);
}