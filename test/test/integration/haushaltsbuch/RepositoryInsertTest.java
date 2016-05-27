package test.integration.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.Entry;
import haushaltsbuch.InsertException;
import haushaltsbuch.persistence.JdbcRepository;
import test.helpers.DatabaseAssertions;
import test.helpers.TestEntry;

public class RepositoryInsertTest extends RepositoryTest
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

    // succeeds because the repository does not pass the supplied ID to the database
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
    DatabaseAssertions dbHelper = getAssertionHelper();

    String superimposed_id = UUID.randomUUID().toString();
    dbHelper.assertNotExists(superimposed_id, _subject.TABLE_NAME);
    _testEntry.setId(superimposed_id);

    String db_assigned_id = _subject.insert(_testEntry);

    dbHelper.assertExists(db_assigned_id, _subject.TABLE_NAME);
    dbHelper.assertNotExists(superimposed_id, _subject.TABLE_NAME);
  }
}
