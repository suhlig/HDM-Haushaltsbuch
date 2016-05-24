package test.integration.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.DeleteException;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.InsertException;
import haushaltsbuch.persistence.JdbcRepository;
import test.helpers.TestDatabase;
import test.helpers.TestEntry;

public class RepositoryDeleteTest
{
  private EntryRepository _subject;
  private TestDatabase _database;
  private TestEntry _testEntry;

  @Before
  public void setUp() throws Exception
  {
    _database = new TestDatabase("jdbc:postgresql:///");
    _subject = new JdbcRepository(_database.getURL(), null, null);
    _testEntry = new TestEntry();
  }

  @After
  public void tearDown() throws Exception
  {
    _subject.close();
    _database.tearDown();
  }

  @Test
  public void testDeleteEmptyString() throws Exception
  {
    try
    {
      _subject.delete("");
      fail("Should have failed");
    }
    catch (Exception e)
    {
      // ok
    }
  }

  @Test
  public void testDeleteExisting() throws InsertException, DeleteException
  {
    String id = _subject.insert(_testEntry);
    Entry inserted = _subject.find(id);
    assertNotNull(inserted);

    Entry deleted = _subject.delete(id);
    assertNull(_subject.find(id));

    assertNotNull(deleted);
    assertEquals(id, deleted.getId());
    assertEquals(inserted, deleted);
  }

  @Test
  public void testDeleteNonExisting() throws InsertException, DeleteException
  {
    String id = UUID.randomUUID().toString();
    assertNull(_subject.find(id));
    assertNull(_subject.delete(id));
  }

  @Test
  public void testDeleteNull() throws Exception
  {
    try
    {
      _subject.delete(null);
      fail("Should have failed");
    }
    catch (Exception e)
    {
      // ok
    }
  }
}
