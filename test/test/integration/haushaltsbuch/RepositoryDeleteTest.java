package test.integration.haushaltsbuch;

import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.EntryRepository;
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
  public void testDelete()
  {
    // fail("Not yet implemented");
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
