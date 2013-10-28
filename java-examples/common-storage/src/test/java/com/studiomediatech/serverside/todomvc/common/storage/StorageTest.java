package com.studiomediatech.serverside.todomvc.common.storage;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StorageTest {

  @Test(expected = IllegalStateException.class)
  public void calling_delete_throws_exception_when_storage_not_selected_for_key() {
    new GuavaCacheStore<Entity>().delete((List<Entity>) null);
  }

  @Test(expected = IllegalStateException.class)
  public void calling_delete_one_throws_when_storage_not_selected_for_key() {
    new GuavaCacheStore<Entity>().delete((Entity) null);
  }

  @Test(expected = IllegalStateException.class)
  public void calling_find_all_throws_when_storage_not_selected_for_key() {
    new GuavaCacheStore<Entity>().findAll();
  }

  @Test(expected = IllegalStateException.class)
  public void calling_save_throws_when_storage_not_selected_for_key() {
    new GuavaCacheStore<Entity>().save((Entity) null);
  }

  @Test
  public void default_storage_constructor_creates_empty_storage() {
    Storage<Entity> storage = new GuavaCacheStore<Entity>();
    Repository<Entity> forKey1 = storage.forKey("key1");
    Assert.assertFalse("Default storage for key1 had entries.", forKey1.findAll().iterator().hasNext());
    Repository<Entity> forKey2 = storage.forKey("key2");
    Assert.assertFalse("Default storage for key2 had entries.", forKey2.findAll().iterator().hasNext());
  }

  private static final class Entity implements Identifiable {
    @Override
    public String getId() {
      return null;
    }
  }
}
