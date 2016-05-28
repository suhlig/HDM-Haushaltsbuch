package test.integration.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.ArgumentException;
import haushaltsbuch.Entry;
import haushaltsbuch.InsertException;
import haushaltsbuch.LookupException;
import haushaltsbuch.persistence.JdbcRepository;
import test.helpers.TestEntry;

public class RepositoryLookupTest extends RepositoryTest
{
  private TestEntry _testEntry;
  private JdbcRepository _subject;

  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    _testEntry = new TestEntry();
    _subject = getRepository();
  }

  @Test
  public void testLookupEmptyString() throws LookupException
  {
    try
    {
      _subject.lookup("");
      fail("Should have failed");
    }
    catch (ArgumentException e)
    {
      // ok
    }
  }

  @Test
  public void testLookupExisting() throws InsertException, LookupException
  {
    String id = _subject.insert(_testEntry);

    Entry found = _subject.lookup(id);

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
  public void testLookupNonExisting() throws LookupException
  {
    assertNull(_subject.lookup(UUID.randomUUID().toString()));
  }

  @Test
  public void testLookupNull() throws LookupException
  {
    try
    {
      _subject.lookup(null);
      fail("Should have failed");
    }
    catch (ArgumentException e)
    {
      // ok
    }
  }
}
