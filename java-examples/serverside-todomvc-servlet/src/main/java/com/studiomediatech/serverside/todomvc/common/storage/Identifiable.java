package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;

/**
 * Interface for items that can provide identification by a public id token.
 * 
 * <p>
 * NOTE: It's probably a bad idea for implementations to use any object instance
 * identity. The purpose of this interface is of course to provide an identity
 * for reliable mapping between transient and persistent state, and therefore it
 * must be agnostic- and applicable to both. An object hash code or pointer
 * reference can be pretty darn hard to reclaim.
 * </p>
 * 
 * @author Olle Törnström <olle@studiomediatech.com>
 * 
 * @param <ID> type of the identification token
 */
public interface Identifiable<ID extends Serializable> {

  /**
   * Returns the identity token.
   * 
   * @return an identity token, never {@code null}
   */
  ID getId();

}