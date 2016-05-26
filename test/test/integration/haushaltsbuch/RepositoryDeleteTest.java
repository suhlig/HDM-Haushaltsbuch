package test.integration.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.ArgumentException;
import haushaltsbuch.DeleteException;
import haushaltsbuch.Entry;
import haushaltsbuch.InsertException;
import haushaltsbuch.LookupException;
import haushaltsbuch.persistence.JdbcRepository;
import test.helpers.TestEntry;

public class RepositoryDeleteTest extends RepositoryTest
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
  public void testDeleteEmptyString() throws DeleteException
  {
    try
    {
      _subject.delete("");
      fail("Should have failed");
    }
    catch (ArgumentException e)
    {
      // ok
    }
  }

  @Test
  public void testDeleteExisting() throws InsertException, LookupException, DeleteException
  {
    String id = _subject.insert(_testEntry);
    Entry inserted = _subject.lookup(id);
    assertNotNull(inserted);

    Entry deleted = _subject.delete(id);
    assertNull(_subject.lookup(id));

    assertNotNull(deleted);
    assertEquals(id, deleted.getId());
    assertEquals(inserted, deleted);
  }

  @Test
  public void testDeleteNonExisting() throws InsertException, LookupException, DeleteException
  {
    String id = UUID.randomUUID().toString();

    if (null != _subject.lookup(id))
      fail("An entry with id " + id + " must not exist for this test");

    assertNull(_subject.delete(id));
  }

  @Test
  public void testDeleteNull() throws DeleteException
  {
    try
    {
      _subject.delete(null);
      fail("Should have failed");
    }
    catch (ArgumentException e)
    {
      // ok
    }
  }
}
