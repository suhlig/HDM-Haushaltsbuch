package test.integration.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.Entry;
import haushaltsbuch.InsertException;
import haushaltsbuch.persistence.JdbcRepository;
import test.helpers.TestDatabase;
import test.helpers.TestEntry;

public class RepositoryInsertTest
{
  private JdbcRepository _subject;
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
  public void testInsertCategoryMissingCategory() throws Exception
  {
    _testEntry.setCategory(null);

    try
    {
      _subject.insert(_testEntry);
      fail("Should have failed");
    }
    catch (InsertException e)
    {
      // ok
    }
  }

  @Test
  public void testInsertDescriptionMissing() throws Exception
  {
    _testEntry.setDescription(null);

    try
    {
      _subject.insert(_testEntry);
      fail("Should have failed");
    }
    catch (InsertException e)
    {
      // ok
    }
  }

  @Test
  public void testInsertDuplicate() throws Exception
  {
    String id0 = _subject.insert(_testEntry);
    Entry clone = _testEntry.clone();
    assertEquals(_testEntry, clone);

    // succeeds because the repository must replace the supplied ID with a generated one
    String id1 = _subject.insert(clone);

    // ensure the repository-generated ids differ between the two entries
    assertNotEquals(id0, id1);
  }

  @Test
  public void testInsertPaymentTypeMissing() throws Exception
  {
    _testEntry.setPaymentType(null);

    try
    {
      _subject.insert(_testEntry);
      fail("Should have failed");
    }
    catch (InsertException e)
    {
      // ok
    }
  }

  @Test
  public void testInsertSrcDestMissing() throws Exception
  {
    _testEntry.setSrcDest(null);

    try
    {
      _subject.insert(_testEntry);
      fail("Should have failed");
    }
    catch (InsertException e)
    {
      // ok
    }
  }

  @Test
  public void testInsertValid() throws Exception
  {
    String superimposed_id = "4711";
    _database.assertNotExists(superimposed_id, _subject.TABLE_NAME);
    _testEntry.setId(superimposed_id);

    String db_assigned_id = _subject.insert(_testEntry);

    _database.assertExists(db_assigned_id, _subject.TABLE_NAME);
    _database.assertNotExists(superimposed_id, _subject.TABLE_NAME);
  }
}
