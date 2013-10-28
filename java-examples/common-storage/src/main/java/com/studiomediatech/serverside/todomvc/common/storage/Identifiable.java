package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;

/**
 * Interface for items that can provide identification by a public id token.
 * 
 * @author Olle Törnström <olle@studiomediatech.com>
 * 
 * @param <ID> type of the identification token
 */
public interface Identifiable<ID extends Serializable> {

  /**
   * Returns the identifier token.
   * 
   * @return an identifier token, never {@code null}
   */
  ID getId();

}