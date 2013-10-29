package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;

/**
 * Provides static methods for creating and getting {@link Store} instances.
 * 
 * @author Olle Törnström <olle@studiomediatech.com>
 * 
 * @see Store
 */
public abstract class Stores<T extends Identifiable<ID>, ID extends Serializable> {

  public static final <T extends Identifiable<ID>, ID extends Serializable> Repository<T, ID> guavaCacheStorage() {
    return null;
  }

}
