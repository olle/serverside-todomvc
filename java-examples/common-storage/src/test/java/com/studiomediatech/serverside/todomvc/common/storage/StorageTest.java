package com.studiomediatech.serverside.todomvc.common.storage;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StorageTest {

  @Test(expected = IllegalStateException.class)
  public void callingDeleteThrowsWhenStorageNotSelectedForKey() {
    new Storage<Entity>().delete((List<Entity>) null);
  }

  @Test(expected = IllegalStateException.class)
  public void callingDeleteOneThrowsWhenStorageNotSelectedForKey() {
    new Storage<Entity>().delete((Entity) null);
  }

  @Test(expected = IllegalStateException.class)
  public void callingFindAllThrowsWhenStorageNotSelectedForKey() {
    new Storage<Entity>().findAll();
  }

  @Test(expected = IllegalStateException.class)
  public void callingSaveThrowsWhenStorageNotSelectedForKey() {
    new Storage<Entity>().save((Entity) null);
  }

  @Test
  public void defaultStorageConstructorCreatesEmptyStorage() {
    Storage<Entity> storage = new Storage<Entity>();
    Storage<Entity> forKey1 = storage.forKey("key1");
    Assert.assertFalse("Default storage for key1 had entries.", forKey1.findAll().iterator().hasNext());
    Storage<Entity> forKey2 = storage.forKey("key2");
    Assert.assertFalse("Default storage for key2 had entries.", forKey2.findAll().iterator().hasNext());
  }

  private static final class Entity implements Identifiable {
    @Override
    public String getId() {
      return null;
    }
  }

}
