package test.integration.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.FindException;
import haushaltsbuch.InsertException;
import haushaltsbuch.persistence.JdbcRepository;
import test.helpers.TestDatabase;
import test.helpers.TestEntry;

public class RepositoryFindTest
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
  public void testFindEmptyString() throws Exception
  {
    try
    {
      _subject.find("");
      fail("Should have failed");
    }
    catch (Exception e)
    {
      // ok
    }
  }

  @Test
  public void testFindExisting() throws InsertException, FindException
  {
    String id = _subject.insert(_testEntry);

    Entry found = _subject.find(id);

    assertNotEquals("inserted and read-back entry are different entities", found, _testEntry);
    assertNotEquals("id of inserted entry is not updated", id, _testEntry.getId());

    String found_id = found.getId();

    assertNotNull(found_id);
    assertFalse(found_id.isEmpty());
    assertTrue("id must look like a GUID", found_id.matches("^[a-z0-9]{8}-([a-z0-9]{4}-){3}[a-z0-9]{12}$"));

    assertNotEquals(_testEntry.getEntryDate(), found.getEntryDate());

    assertEquals("the id we searched for must be the same as the entry's id", id, found_id);
    assertEquals(_testEntry.getCategory(), found.getCategory());
    assertEquals(_testEntry.getDescription(), found.getDescription());
    assertEquals(_testEntry.getPaymentType(), found.getPaymentType());
    assertEquals(_testEntry.getSrcDst(), found.getSrcDst());
    assertEquals(_testEntry.getValue(), found.getValue());
  }

  @Test
  public void testFindNonExisting() throws FindException
  {
    assertNull(_subject.find(UUID.randomUUID().toString()));
  }

  @Test
  public void testFindNull() throws Exception
  {
    try
    {
      _subject.find(null);
      fail("Should have failed");
    }
    catch (Exception e)
    {
      // ok
    }
  }
}
