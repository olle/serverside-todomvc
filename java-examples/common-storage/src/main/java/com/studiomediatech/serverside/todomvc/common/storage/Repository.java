package com.studiomediatech.serverside.todomvc.common.storage;

import java.io.Serializable;

/**
 * Interface for repository operations available on entities of a given type
 * with a specific identifier type.
 * 
 * @author Olle Törnström <olle@studiomediatech.com>
 * 
 * @param <T> the type of the entities that are managed by the repository
 * @param <ID> type of identifier for entities
 * 
 * @see Identifiable
 */
public interface Repository<T extends Identifiable<ID>, ID extends Serializable> {

  /**
   * Returns the number of entities found.
   * 
   * @return the number of entities
   */
  long count();

  /**
   * Returns all the maintained entities.
   * 
   * @return all entities or {@code null} if none exist
   */
  Iterable<T> findAll();

  /**
   * Returns all the maintained entities matching any of the the given ids.
   * 
   * @param ids of the entities to find
   * 
   * @return all entities found or {@code null} if none existed
   */
  Iterable<T> findAll(Iterable<ID> ids);

  /**
   * Returns the entity with the given id.
   * 
   * return the entity or {@code null} if not found
   */
  T findOne(ID id);

  /**
   * Saves the given entity in the repository, overwriting any previously saved
   * version and <strong>returning a new, possibly modified copy of the saved
   * entity</strong>.
   * 
   * @param entity to save
   * 
   * @return a new, saved and <strong>possibly modified</strong>, entity or
   *         {@code null} if saving failed
   */
  T save(T entity);

  /**
   * Saves the given entities, overwriting any previously saved versions.
   * 
   * @param entities to save
   * 
   * @return the saved entities only, not failures and never {@code null}
   */
  Iterable<T> save(Iterable<? extends T> entities);

  /**
   * Deletes the given entity from the repository.
   * 
   * @param entity to delete
   */
  void delete(T entity);

  /**
   * Deletes the entity identified by the given id.
   * 
   * @param id of the entity to delete
   */
  void delete(ID id);

  /**
   * Deletes the given entities.
   * 
   * @param entities to delete
   */
  void delete(Iterable<? extends T> entities);

  /**
   * Deletes all entities from the repository.
   */
  void deleteAll();

  /**
   * Checks whether an entity with the given id can be found in the repository.
   * 
   * @param id of the entity to check for
   * 
   * @return {@code true} if an entry with the given id is found, {@code false}
   *         otherwise
   */
  boolean exists(ID id);

}