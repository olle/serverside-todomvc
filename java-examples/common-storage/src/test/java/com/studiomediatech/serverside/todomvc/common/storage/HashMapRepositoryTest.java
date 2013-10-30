package com.studiomediatech.serverside.todomvc.common.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashMapRepositoryTest {

  private static final class Entity implements Identifiable<String> {

    private final String id;
    private String name;

    private Entity(String id) {
      this.id = id;
    }

    @Override
    public String getId() {
      return this.id;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  private HashMapRepository<Entity, String> repository;

  @Before
  public void setUp() {
    this.repository = new HashMapRepository<>();
  }

  @Test
  public void basic_repository_operations_are_supported() {

    Entity original = new Entity("e1");

    Assert.assertEquals("Was not empty", 0, this.repository.count());
    Assert.assertNull("Found something", this.repository.findAll());
    Assert.assertNull("Found entity", this.repository.findOne("e1"));
    Assert.assertFalse("Existed", this.repository.exists("e1"));
    Assert.assertNull("Found entity", this.repository.findOne(""));
    Assert.assertNull("Found entity", this.repository.findOne(null));

    this.repository.delete(original);
    Assert.assertEquals("Was not empty", 0, this.repository.count());
    Assert.assertNull("Found something", this.repository.findAll());
    Assert.assertNull("Found entity", this.repository.findOne("e1"));
    Assert.assertFalse("Existed", this.repository.exists("e1"));

    this.repository.save(original);
    this.repository.save(original);

    Entity saved = this.repository.save(original);
    Assert.assertTrue("Didn't exist", this.repository.exists("e1"));

    Assert.assertEquals("Was empty", 1, this.repository.count());
    Assert.assertNotNull("Couldn't find entity", this.repository.findOne("e1"));
    Assert.assertNull("Found entity", this.repository.findOne(""));
    Assert.assertNull("Found entity", this.repository.findOne(null));

    Iterable<Entity> all = this.repository.findAll();

    Assert.assertNotNull("Nothing was found", all);

    Iterator<Entity> iter1 = all.iterator();

    Assert.assertTrue("Nothing was found", iter1.hasNext());
    Assert.assertSame("Not same as saved", saved, iter1.next());
    Assert.assertFalse("Has more", iter1.hasNext());

    saved = null;
    original = null;

    Assert.assertNull("Not empty", original);
    Assert.assertNull("Not empty", saved);

    Assert.assertEquals("Was empty", 1, this.repository.count());
    Assert.assertNotNull("Couldn't find entity", this.repository.findOne("e1"));

    Entity loaded = this.repository.findOne("e1");

    Assert.assertNotNull("Was empty", loaded);
    Assert.assertEquals("Not same id", "e1", loaded.getId());

    this.repository.delete("e1");

    Assert.assertEquals("Was not empty", 0, this.repository.count());
    Assert.assertNull("Found something", this.repository.findAll());
    Assert.assertNull("Found entity", this.repository.findOne("e1"));
    Assert.assertNull("Found entity", this.repository.findOne(""));
    Assert.assertNull("Found entity", this.repository.findOne(null));
    Assert.assertNotNull("Was empty", loaded);

    Entity another = new Entity("e2");

    this.repository.save(another);
    Assert.assertNotNull("Couldn't find entity", this.repository.findOne("e2"));
    Assert.assertEquals("Was empty", 1, this.repository.count());

    this.repository.delete(another);
    Assert.assertNull("Found entity", this.repository.findOne("e2"));
    Assert.assertEquals("Was not empty", 0, this.repository.count());

    Assert.assertNotNull("Was empty", another);

    this.repository.save(another);
    Assert.assertNotNull("Couldn't find entity", this.repository.findOne("e2"));
    Assert.assertEquals("Was empty", 1, this.repository.count());

    this.repository.deleteAll();
    Assert.assertNull("Found entity", this.repository.findOne("e2"));
    Assert.assertEquals("Was not empty", 0, this.repository.count());

    Entity foo = new Entity("foo");
    Entity bar = new Entity("bar");
    Entity baz = new Entity("baz");

    Iterable<Entity> justNothing = this.repository.findAll(Arrays.asList("foo", "bar", "baz"));
    Assert.assertNull("Found something", justNothing);

    this.repository.save(Arrays.asList(foo, bar));

    Iterable<Entity> allTwo = this.repository.findAll(Arrays.asList("foo", "bar", "baz"));
    Assert.assertNotNull("Nothing was found", allTwo);
    ArrayList<Entity> allTwoList = Lists.newArrayList(allTwo);

    Assert.assertTrue("Missing foo", allTwoList.contains(foo));
    Assert.assertTrue("Missing bar", allTwoList.contains(bar));
    Assert.assertFalse("Contains baz", allTwoList.contains(baz));

    this.repository.save(baz);
    Iterable<Entity> allThree = this.repository.findAll(Arrays.asList("foo", "bar", "baz"));
    Assert.assertNotNull("Nothing was found", allThree);
    ArrayList<Entity> allThreeList = Lists.newArrayList(allThree);

    Assert.assertTrue("Missing foo", allThreeList.contains(foo));
    Assert.assertTrue("Missing bar", allThreeList.contains(bar));
    Assert.assertTrue("Missing baz", allThreeList.contains(baz));

    this.repository.delete(Arrays.asList(foo, baz));

    Iterable<Entity> justOne = this.repository.findAll(Arrays.asList("foo", "bar", "baz"));
    Assert.assertNotNull("Nothing was found", justOne);
    ArrayList<Entity> justOneList = Lists.newArrayList(justOne);

    Assert.assertFalse("Contains foo", justOneList.contains(foo));
    Assert.assertTrue("Missing bar", justOneList.contains(bar));
    Assert.assertFalse("Contains baz", justOneList.contains(baz));
  }
}
